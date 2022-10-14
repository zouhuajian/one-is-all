package org.coastline.one.common.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

import java.io.IOException;

/**
 *
 * @see <a href="https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/current/java-rest-low-usage-requests.html">official documents</a>
 * @author Jay.H.Zou
 * @date 2022/10/13
 */
public class TestESLowLevelRestClient {

    public static void main(String[] args) throws IOException {
        RestClient restClient = RestClient.builder(
                new HttpHost("localhost", 9200, "http"),
                new HttpHost("localhost", 9201, "http")).build();
        Request request = new Request(
                "GET",
                "/");
        Response response = restClient.performRequest(request);

        restClient.close();
    }
}
