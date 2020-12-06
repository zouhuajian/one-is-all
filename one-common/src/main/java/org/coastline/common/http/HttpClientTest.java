package org.coastline.common.http;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.SocketConfig;
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


    public static void main(String[] args) throws IOException {
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
                .setDefaultSocketConfig(socketConfig)
                .setConnectionManager(connManager)
                .setMaxConnPerRoute(4)
                //.setMaxConnTotal(10)
                .setConnectionTimeToLive(1, TimeUnit.HOURS)
                .build();
        for (int i = 0; i < 10; i++) {
            HttpGet httpGet = new HttpGet("localhost:8080/first");
            String responseBody = httpClient.execute(httpGet, httpResponse -> {
                System.out.println(httpResponse);
                return null;
            });
        }

    }
}
