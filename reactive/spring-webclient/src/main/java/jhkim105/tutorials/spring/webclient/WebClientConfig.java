package jhkim105.tutorials.spring.webclient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

  @Bean
  public WebClient webClient(WebClient.Builder builder) {
    return builder.build();
  }


  /**
   * Global customize
   */
  @Bean
  public WebClientCustomizer webClientCustomizer() {
    return customizer -> {
      HttpClient httpClient = HttpClient.create()
          .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
          .responseTimeout(Duration.ofMillis(5000))
          .doOnConnected(conn ->
              conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                  .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));

      customizer.clientConnector(new ReactorClientHttpConnector(httpClient));
    };
  }

}
