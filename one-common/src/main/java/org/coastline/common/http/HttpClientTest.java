package org.coastline.common.http;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.SocketConfig;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author zouhuajian
 * @date 2020/12/6
 */
public class HttpClientTest {


    public static void main(String[] args) throws IOException, InterruptedException {
        CloseableHttpClient httpClient;

        PoolingHttpClientConnectionManager connManager;
        int timeout = 5000;
        RequestConfig requestConfig = RequestConfig.custom()
                // 连接超时时间
                .setConnectTimeout(timeout)
                // 读超时时间（等待数据超时时间）
                .setSocketTimeout(timeout)
                .build();
        SocketConfig socketConfig = SocketConfig.custom()
                .setSoKeepAlive(false)
                .setSoTimeout(timeout)
                .build();
        connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(5);
        connManager.setDefaultMaxPerRoute(4);
        connManager.setDefaultMaxPerRoute(10);
        httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                /*.setDefaultSocketConfig(socketConfig)
                .setConnectionManager(connManager)*/
                .setConnectionTimeToLive(1, TimeUnit.HOURS)
                .build();
        for (int i = 0; i < 10; i++) {
            TimeUnit.SECONDS.sleep(1);
            HttpPost post = new HttpPost("");
            post.setEntity(new StringEntity("{\n" +
                    "    \"msgtype\": \"text\",\n" +
                    "    \"text\": {\n" +
                    "        \"content\": \"广州今日天气：29度，大部分多云，降雨概率：60%\"\n" +
                    "    }\n" +
                    "}\n", ContentType.APPLICATION_JSON));
            /*String responseBody = httpClient.execute(post, httpResponse -> {
                System.out.println(httpResponse);
                return null;
            });*/

            CloseableHttpResponse execute = httpClient.execute(post);
            System.out.println(execute);
        }

    }
}
