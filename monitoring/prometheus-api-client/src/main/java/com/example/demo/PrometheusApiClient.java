package com.example.demo;


import com.jayway.jsonpath.JsonPath;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class PrometheusApiClient {

  public List<Map<String, String>> activeServerList() {
    try(CloseableHttpClient client = HttpClientBuilder.create().build();
        CloseableHttpResponse response = client.execute(new HttpGet("http://localhost:9090/api/v1/targets?state=active"))) {
      List<Map<String, String>> list = JsonPath.parse(EntityUtils.toString(response.getEntity())).read("$.data.activeTargets.[*].labels.instance");
      return list;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
