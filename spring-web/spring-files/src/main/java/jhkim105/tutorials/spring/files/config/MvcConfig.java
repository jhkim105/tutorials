package jhkim105.tutorials.spring.files.config;

import jhkim105.tutorials.spring.files.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
@Slf4j
public class MvcConfig implements WebMvcConfigurer {

  private final AppProperties appProperties;

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/upload-form2").setViewName("upload-form2");
  }


  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.setOrder(-1);
    appProperties.getResourceMappings().values().forEach(r ->
        registry.addResourceHandler(r.getPath())
            .addResourceLocations(r.getLocations())
    );
  }

}