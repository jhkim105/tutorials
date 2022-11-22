package jhkim105.tutorials.spring.cloud.microservices.web.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProductServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProductServiceApplication.class, args);
  }

}
