package jhkim105.tutorials.osiv.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class JpaConfig {
  @Bean
  public OpenEntityManagerInViewInterceptor openEntityManagerInViewInterceptor() {
    return new OpenEntityManagerInViewInterceptor();
  }

  @Bean
  public WebMvcConfigurer openEntityManagerInViewInterceptorConfigurer(
      OpenEntityManagerInViewInterceptor interceptor) {
    return new WebMvcConfigurer() {

      @Override
      public void addInterceptors(InterceptorRegistry registry) {
        registry.addWebRequestInterceptor(interceptor).addPathPatterns("/users/osiv/**");
      }

    };
  }
}
