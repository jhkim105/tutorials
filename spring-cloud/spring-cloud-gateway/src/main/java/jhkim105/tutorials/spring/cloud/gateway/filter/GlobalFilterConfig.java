package jhkim105.tutorials.spring.cloud.gateway.filter;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalFilterConfig {


  @Bean
  public GlobalFilter customGlobalPreFilter() {
    return new LoggingGlobalPreFilter();
  }

  @Bean
  public GlobalFilter customGlobalPostFilter() {
    return new LoggingGlobalPostFilter();
  }


}
