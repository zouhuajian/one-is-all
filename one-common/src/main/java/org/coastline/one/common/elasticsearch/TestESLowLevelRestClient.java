package org.coastline.one.common.elasticsearch;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

import java.io.IOException;

/**
 * @author Jay.H.Zou
 * @date 2022/10/13
 * @see <a href="https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/current/java-rest-low-usage-requests.html">official documents</a>
 */
public class TestESLowLevelRestClient {

    public static void main(String[] args) throws IOException {
        RestClient restClient = RestClient.builder(
                new HttpHost("xxx", 9200, "http")
        ).build();

        Request put = new Request(
                "PUT",
                "/customer/_doc/1");
        put.setJsonEntity("{\n" +
                "  \"name\": \"John Doe\"\n" +
                "}");

        Response response = restClient.performRequest(put);
        HttpEntity entity = response.getEntity();
        System.out.println(entity);
        Response getResponse = restClient.performRequest(new Request("GET", "/customer/_doc/1"));
        System.out.println(getResponse);
        restClient.close();
    }
}
