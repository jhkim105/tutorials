package jhkim105.tutorials.config;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.metamodel.Type;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class RepositoryRestConfig implements RepositoryRestConfigurer {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
    config.exposeIdsFor(entityManager.getMetamodel().getEntities().stream()
        .map(Type::getJavaType)
        .toArray(Class[]::new));

    cors.addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")
        .allowedHeaders("*")
//        .exposedHeaders("header1", "header2")
//        .allowCredentials(false).maxAge(3600)
    ;
    config.setReturnBodyForPutAndPost(true);
  }


}
