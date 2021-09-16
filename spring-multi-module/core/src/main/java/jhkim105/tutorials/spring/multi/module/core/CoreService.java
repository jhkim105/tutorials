package jhkim105.tutorials.spring.multi.module.core;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@EnableConfigurationProperties(ServiceProperties.class)
@RequiredArgsConstructor
public class CoreService {

  private final ServiceProperties serviceProperties;

  public String getAppName() {
    return serviceProperties.getAppName();
  }
}
