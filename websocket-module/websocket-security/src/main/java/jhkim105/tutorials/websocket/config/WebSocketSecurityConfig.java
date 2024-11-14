package jhkim105.tutorials.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;

@EnableWebSocketSecurity
public class WebSocketSecurityConfig {


    @Bean
    AuthorizationManager<Message<?>> authorizationManager(MessageMatcherDelegatingAuthorizationManager.Builder messages) {
        messages
            .simpDestMatchers("/user/queue/errors").permitAll()
            .simpMessageDestMatchers("/app/**").authenticated()
            .simpSubscribeDestMatchers("/user/**", "/topic/**").authenticated()
            .simpTypeMatchers(SimpMessageType.CONNECT).authenticated()
            .nullDestMatcher().permitAll()
            .anyMessage().denyAll();

        return messages.build();
    }

}
