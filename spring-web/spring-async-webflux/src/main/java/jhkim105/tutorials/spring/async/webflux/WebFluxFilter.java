package jhkim105.tutorials.spring.async.webflux;

import java.time.Duration;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class WebFluxFilter implements WebFilter {

    @Override
    public Mono filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        return Mono
          .delay(Duration.ofMillis(200))
          .then(
            webFilterChain.filter(serverWebExchange)
          );
    }
}