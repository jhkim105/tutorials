package com.example.demo;

import com.google.api.services.youtube.model.ChannelListResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class YouTubeApiTest {

  @Autowired
  YouTubeApi youTubeApi;

  @Test
  @Disabled
  public void getChannels() {
    String accessToken = "ya29.A0AfH6SMCcTUDZd8KORsTDTiRKaotPq7Lk_xxJaz5BMkYhzKLKLe0433DxIB8G0_x3soCUaF3W6o6G5OslAtqv_BiNjIh4LZ25zUtZbZgOkgWA3ruCfR67zlXMfB1Yiq56emjtGOEkycuWuQoFDOFSBPxdgqSr4g";
    ChannelListResponse channelListResponse = youTubeApi.getChannels(accessToken);
    log.debug("{}", channelListResponse);
  }
}