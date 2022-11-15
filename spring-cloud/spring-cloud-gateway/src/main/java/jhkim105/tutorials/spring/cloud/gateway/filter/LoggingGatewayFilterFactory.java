package jhkim105.tutorials.spring.cloud.gateway.filter;

import java.util.Arrays;
import java.util.List;
import jhkim105.tutorials.spring.cloud.gateway.filter.LoggingGatewayFilterFactory.Config;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingGatewayFilterFactory extends AbstractGatewayFilterFactory<Config> {

  public LoggingGatewayFilterFactory() {
    super(Config.class);
  }

  @Override
  public List<String> shortcutFieldOrder() {
    return Arrays.asList("baseMessage", "preLogger", "postLogger");
  }

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      if (config.isPreLogger()) {
        log.info("Pre GatewayFilter logging: " + config.getBaseMessage());
      }

      return chain.filter(exchange)
          .then(Mono.fromRunnable(() -> {
            if (config.isPostLogger()) {
              log.info("Post GatewayFilter logging: " + config.getBaseMessage());
            }
          }));
    };
  }


  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Config {
    private String baseMessage;
    private boolean preLogger;
    private boolean postLogger;
  }
}
