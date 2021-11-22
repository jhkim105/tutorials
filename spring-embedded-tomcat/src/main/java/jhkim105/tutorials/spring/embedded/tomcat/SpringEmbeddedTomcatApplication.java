package jhkim105.tutorials.spring.embedded.tomcat;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class SpringEmbeddedTomcatApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringEmbeddedTomcatApplication.class, args);
  }

  @Value("${server.tomcat.accesslog.ignore-uris}")
  List<String> ignoreUris;

  @Bean
  public FilterRegistrationBean<AccessLogFilter> accessLogFilter(){
    log.info("{}", ignoreUris);
    FilterRegistrationBean<AccessLogFilter> registrationBean
        = new FilterRegistrationBean<>();

    registrationBean.setFilter(new AccessLogFilter());
    registrationBean.addUrlPatterns(ignoreUris.toArray(new String[0]));

    return registrationBean;
  }
}
