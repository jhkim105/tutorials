package jhkim105.tutorials.security;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

@Slf4j
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return new JwtAuthenticationToken(userPrincipal, authorities(userPrincipal));

    }

    private Set<GrantedAuthority> authorities(UserPrincipal userPrincipal) {
        return Arrays.stream(userPrincipal.authority().split(",")).map(authority -> (GrantedAuthority) () -> authority)
            .collect(Collectors.toSet());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(JwtAuthenticationToken.class);
    }
}
