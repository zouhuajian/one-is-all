package org.coastline.one.core.tool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Jay.H.Zou
 * @date 2022/4/19
 */
public class HttpClientTool {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientTool.class);

    public static OkHttpClient getConfigClient() {
        return getClient(1, 1, false);
    }

    public static OkHttpClient getClient(int maxIdleConnections, int maxRequestsPerHost, boolean enableRetry) {
        Duration duration = Duration.ofSeconds(3);
        ConnectionPool connectionPool = new ConnectionPool(maxIdleConnections, 5, TimeUnit.MINUTES);
        int core = Runtime.getRuntime().availableProcessors();
        Dispatcher dispatcher = new Dispatcher(new ThreadPoolExecutor(core, core,
                1, TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(core * 4),
                new ThreadFactoryBuilder().setNameFormat("okhttp-dispatcher-%d").setDaemon(true).build(),
                new ThreadPoolExecutor.CallerRunsPolicy()));
        dispatcher.setMaxRequestsPerHost(maxRequestsPerHost);
        dispatcher.setMaxRequests(maxRequestsPerHost);
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .retryOnConnectionFailure(false)
                .callTimeout(Duration.ofSeconds(10))
                .readTimeout(duration)
                .writeTimeout(duration)
                .dispatcher(dispatcher)
                .connectionPool(connectionPool);
        if (enableRetry) {
            builder.addInterceptor(OkHttpRetryInterceptor.create(3));
        }
        return builder.build();
    }

    public static OkHttpClient getClientDefault() {
        Duration duration = Duration.ofSeconds(3);
        return new OkHttpClient().newBuilder()
                .callTimeout(Duration.ofSeconds(10))
                .readTimeout(duration)
                .writeTimeout(duration)
                .build();
    }


    /**
     * http retry interceptor
     */
    public static class OkHttpRetryInterceptor implements Interceptor {
        private static final long RETRY_INTERVAL_MILLIS = 200;
        public int maxRetryTimes;

        public OkHttpRetryInterceptor(int maxRetryTimes) {
            this.maxRetryTimes = maxRetryTimes;
        }

        public static OkHttpRetryInterceptor create(int maxRetryTimes) {
            return new OkHttpRetryInterceptor(maxRetryTimes);
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            int retryCount = 1;
            while (!response.isSuccessful() && retryCount <= maxRetryTimes) {
                long wait = RETRY_INTERVAL_MILLIS * retryCount;
                LOGGER.warn("okhttp retry: retry count = {}, wait = {}ms, response = {}", retryCount, wait, response);
                response.close();
                try {
                    TimeUnit.MILLISECONDS.sleep(wait);
                } catch (InterruptedException ignored1) {
                }
                retryCount++;
                response = chain.proceed(request);
            }
            return response;
        }

    }
}
