package jhkim105.tutorials.spring.http.logging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@SpringBootApplication
public class SpringHttpLoggingApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringHttpLoggingApplication.class, args);
  }


  @Bean
  @ConditionalOnProperty(name="commons-request-logging-filter.enabled", havingValue = "true")
  CommonsRequestLoggingFilter commonsRequestLoggingFilter() {
    CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
    filter.setIncludeQueryString(true);
    filter.setIncludePayload(true);
    filter.setMaxPayloadLength(10000);
    filter.setIncludeHeaders(true);
    filter.setAfterMessagePrefix("REQUEST: ");
    return filter;
  }

}
