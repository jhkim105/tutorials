package com.example.demo;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.CdnSettings;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.LiveBroadcast;
import com.google.api.services.youtube.model.LiveBroadcastListResponse;
import com.google.api.services.youtube.model.LiveStream;
import com.google.api.services.youtube.model.LiveStreamListResponse;
import com.google.api.services.youtube.model.LiveStreamSnippet;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
/**
 * https://developers.google.com/youtube/v3/docs?hl=ko
 * https://developers.google.com/youtube/v3/live/docs
 */
public class YouTubeApi {

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

  public LiveStreamListResponse getLiveStreamLists(String accessToken) {
    try {
      YouTube.LiveStreams.List request = youTube.liveStreams()
          .list("id,snippet,cdn")
          .setMine(true)
          .setOauthToken(accessToken);

      return request.execute();
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }


  public LiveStream getLiveStreamByStreamName(String accessToken, String streamName) {
    try {
      YouTube.LiveStreams.List request = youTube.liveStreams()
          .list("id,snippet,cdn")
          .setMine(true)
          .setOauthToken(accessToken);
      LiveStreamListResponse liveStreamListResponse = request.execute();
      Optional<LiveStream> liveStreamOptional = liveStreamListResponse.getItems().stream().filter(o -> o.getCdn().getIngestionInfo().getStreamName().equals(streamName))
          .findAny();
      return liveStreamOptional.orElse(null);
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }

  }




  public LiveStream createLiveStream(String accessToken) {
    try {
      LiveStream content = new LiveStream();
      CdnSettings cdnSettings = new CdnSettings();
      cdnSettings.setFrameRate("30fps");
      cdnSettings.setIngestionType("rtmp");
      cdnSettings.setResolution("720p");
      content.setCdn(cdnSettings);

      LiveStreamSnippet liveStreamSnippet = new LiveStreamSnippet();
      liveStreamSnippet.setTitle("youtube-broadcast-remote-studio"); //TODO
      content.setSnippet(liveStreamSnippet);

      YouTube.LiveStreams.Insert request = youTube.liveStreams()
          .insert("snippet, cdn", content)
          .setOauthToken(accessToken);
      return request.execute();
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }

  }

  public void deleteLiveStream(String accessToken, String streamId) {
    try {
      YouTube.LiveStreams.Delete request = youTube.liveStreams().delete(streamId).setOauthToken(accessToken);
      request.execute();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  public LiveBroadcastListResponse getLiveBroadcasts(String accessToken) {
    try {
      YouTube.LiveBroadcasts.List request = youTube.liveBroadcasts()
          .list("snippet,contentDetails,status")
          .setMine(true)
          .setOauthToken(accessToken);
      return request.execute();
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  public LiveBroadcast getUpcomingLiveBroadcast(String accessToken) {
    try {
      YouTube.LiveBroadcasts.List request = youTube.liveBroadcasts()
          .list("snippet,contentDetails,status")
          .setBroadcastStatus("upcoming")
          .setOauthToken(accessToken);
      LiveBroadcastListResponse liveBroadcastListResponse =  request.execute();
      return liveBroadcastListResponse.getItems().get(0);
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  public LiveBroadcast getLiveBroadcast(String accessToken, String broadcastId) {
    try {
      YouTube.LiveBroadcasts.List request = youTube.liveBroadcasts()
          .list("snippet,contentDetails,status")
          .setId(broadcastId)
          .setOauthToken(accessToken);
      LiveBroadcastListResponse liveBroadcastListResponse =  request.execute();
      return liveBroadcastListResponse.getItems().get(0);
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }



  public LiveBroadcast bindLiveBroadcast(String accessToken, String broadcastId, String streamId) {
    try {
      YouTube.LiveBroadcasts.Bind request = youTube.liveBroadcasts().bind(broadcastId, "id")
          .setStreamId(streamId)
          .setOauthToken(accessToken);
      return request.execute();
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  public LiveBroadcast updateLiveBroadcast(String accessToken, LiveBroadcast liveBroadcast) {
    try {
      LiveBroadcast content = new LiveBroadcast();
      content.setId(liveBroadcast.getId());
      content.setSnippet(liveBroadcast.getSnippet());
      log.debug("{}", content);
      YouTube.LiveBroadcasts.Update request = youTube.liveBroadcasts().update("id,snippet", content).setOauthToken(accessToken);
      return request.execute();
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }


}
