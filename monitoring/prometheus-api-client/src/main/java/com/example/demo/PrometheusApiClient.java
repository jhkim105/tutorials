package com.example.demo;


import com.jayway.jsonpath.JsonPath;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

@RequiredArgsConstructor
@Slf4j
public class PrometheusApiClient {

  private final String url;

  public List<String> activeServerList() {
    try(CloseableHttpClient client = HttpClientBuilder.create().build();
        CloseableHttpResponse response = client.execute(new HttpGet(url + "/targets?state=active"))) {
      String responseString = EntityUtils.toString(response.getEntity());
      log.debug(responseString);
      List<String> list = JsonPath.parse(responseString).read("$.data.activeTargets.[*].labels.instance");
      return list;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
