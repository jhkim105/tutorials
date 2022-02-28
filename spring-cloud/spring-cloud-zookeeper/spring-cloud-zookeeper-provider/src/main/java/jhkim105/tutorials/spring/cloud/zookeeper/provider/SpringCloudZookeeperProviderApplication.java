package jhkim105.tutorials.spring.cloud.zookeeper.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudZookeeperProviderApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringCloudZookeeperProviderApplication.class, args);
  }

}
