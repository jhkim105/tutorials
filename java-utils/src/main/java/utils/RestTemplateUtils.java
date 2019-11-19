/*
 *
 *  * MPP(Multiple Push) version 1.0
 *  *
 *  *  Copyright ⓒ 2018 kt corp. All rights reserved.
 *  *
 *  *  This is a proprietary software of kt corp, and you may not use this file except in
 *  *  compliance with license agreement with kt corp. Any redistribution or use of this
 *  *  software, with or without modification shall be strictly prohibited without prior written
 *  *  approval of kt corp, and the copyright notice above does not evidence any actual or
 *  *  intended publication of such software.
 *
 */

package utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.cert.X509Certificate;
import java.util.Arrays;

@Slf4j
public final class RestTemplateUtils {

  private static final int DEFAULT_TIMEOUT = 5000;

  private RestTemplateUtils() {}

  public static HttpHeaders jsonHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
    return headers;
  }

  public static RestTemplate getRestTemplateSslIgnored() {
    return getRestTemplateSslIgnored(DEFAULT_TIMEOUT);
  }

  public static RestTemplate getRestTemplateSslIgnored(int timeout) {
    TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
    SSLContext sslContext = null;
    try {
      sslContext = org.apache.http.ssl.SSLContexts.custom()
          .loadTrustMaterial(null, acceptingTrustStrategy)
          .build();
    } catch (Exception ex) {
      log.warn(ex.toString());
    }
    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

    RequestConfig config = RequestConfig.custom()
        .setConnectTimeout(timeout).setConnectionRequestTimeout(timeout).setSocketTimeout(timeout).build();

    CloseableHttpClient httpClient = HttpClients.custom()
        .setSSLSocketFactory(csf)
        .setDefaultRequestConfig(config).build();

    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
    requestFactory.setHttpClient(httpClient);

    return new RestTemplate(requestFactory);
  }

  public static RestTemplate getRestTemplate(int timeout) {
    RequestConfig config = RequestConfig.custom()
        .setConnectTimeout(timeout)
        .setConnectionRequestTimeout(timeout)
        .setSocketTimeout(timeout)
        .build();

    CloseableHttpClient client = HttpClientBuilder.create()
        .setDefaultRequestConfig(config)
        .build();

    return new RestTemplate(new HttpComponentsClientHttpRequestFactory(client));
  }

  public static RestTemplate getRestTemplate() {
    return getRestTemplate(DEFAULT_TIMEOUT);
  }
}