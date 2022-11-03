package com.example.demo;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YouTubeConfig {


  @Bean
  public YouTube youtube() {
    return new YouTube.Builder(new NetHttpTransport(),
          JacksonFactory.getDefaultInstance(),
          arg0 -> {})
        .setApplicationName("Studio")
        .build();
  }
}
