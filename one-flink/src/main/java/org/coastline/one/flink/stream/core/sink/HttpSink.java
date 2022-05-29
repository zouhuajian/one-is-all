package org.coastline.one.flink.stream.core.sink;

import okhttp3.*;
import org.apache.flink.configuration.ConfigOptions;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.coastline.one.flink.common.util.HttpClientTool;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Jay.H.Zou
 * @date 2022/5/29
 */
public class HttpSink<T> extends RichSinkFunction<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpSink.class);

    public static final MediaType TEXT_PLAIN = MediaType.parse("text/plain;charset=utf-8");

    protected OkHttpClient httpClient;
    private String url;

    @Override
    public void open(Configuration parameters) throws Exception {
        Configuration configuration = (Configuration) getRuntimeContext().getExecutionConfig().getGlobalJobParameters();
        url = configuration.get(ConfigOptions.key("prometheus.url").stringType().noDefaultValue());
        httpClient = HttpClientTool.getClientDefault();
    }

    @Override
    public void invoke(T value, Context context) throws Exception {
        // fake
        byte[] data = transform(value);
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(data, TEXT_PLAIN))
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LOGGER.error("onFailure: write data into prometheus error, url = {}", url, e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    LOGGER.error("onResponse: write data into prometheus error, url = {}, response = {}", url, response);
                }
                response.close();
            }
        });
    }

    private byte[] transform(T value) {
        return new byte[0];
    }

}
