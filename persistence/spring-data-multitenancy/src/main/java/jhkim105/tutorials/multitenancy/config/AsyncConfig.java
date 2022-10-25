package jhkim105.tutorials.multitenancy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
public class AsyncConfig {

  @Bean
  public TaskDecorator TenantAwareTaskDecorator() {
    return new TenantTaskDecorator();
  }

}
