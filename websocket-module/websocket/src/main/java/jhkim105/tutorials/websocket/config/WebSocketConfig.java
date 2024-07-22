package jhkim105.tutorials.websocket.config;


import jhkim105.tutorials.websocket.interceptor.MyChannelInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/ws").setAllowedOrigins("*");// websocket handshake
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.setApplicationDestinationPrefixes("/app"); // STOMP messages whose destination header begins with /app are routed to @MessageMapping methods in @Controller classes.
    config.enableSimpleBroker("/topic", "/queue"); //	Use the built-in message broker for subscriptions and broadcasting and route messages whose destination header begins with /topic or /queue to the broker.
  }

  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    registration.interceptors(new MyChannelInterceptor());
  }


}
