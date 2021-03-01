package com.example.demo;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ChannelListResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class YouTubeApiHelper {

  private final YouTube youTube;


  public ChannelListResponse getChannels(String accessToken) {
    try {
      YouTube.Channels.List request = youTube.channels()
          .list("id")
          .setMine(true)
          .setOauthToken(accessToken);

      return request.execute();
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }



}
