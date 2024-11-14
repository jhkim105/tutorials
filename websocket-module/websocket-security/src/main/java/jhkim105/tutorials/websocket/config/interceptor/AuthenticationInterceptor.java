package jhkim105.tutorials.websocket.config.interceptor;

import java.security.Principal;
import java.util.Collection;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;


@Slf4j
public class AuthenticationInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
            MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String authorization = String.valueOf(accessor.getNativeHeader("Authorization"));
            accessor.setUser(authentication(authorization));
        }
        return message;
    }

    private Principal authentication(String authorization) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            "id01", null, authorities(authorization));
        return authenticationToken;
    }

    private Collection<? extends GrantedAuthority> authorities(String authorization) {
        return Set.of((GrantedAuthority) () -> "USER");
    }


}