package jhkim105.tutorials.rabbitmq.monitoring;

import com.rabbitmq.http.client.Client;
import com.rabbitmq.http.client.ClientParameters;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class AmqpConfig {

  @Bean
  public Client rabbitmqClient(Environment env) throws MalformedURLException, URISyntaxException {
    String apiUrl = "http://localhost:15672/api";
    String username = "guest";
    String password = "guest";
    return new Client(new ClientParameters().url(apiUrl).username(username).password(password));
  }
}
