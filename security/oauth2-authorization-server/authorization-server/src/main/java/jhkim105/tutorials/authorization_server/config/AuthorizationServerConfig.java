package jhkim105.tutorials.authorization_server.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
public class AuthorizationServerConfig {


  @Bean
  @Order(1)
  public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
    OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
    http
        .exceptionHandling(exceptions ->
            exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
        )
    ;
    return http.build();
  }

  @Bean
  public RegisteredClientRepository registeredClientRepository() {
    RegisteredClient registeredClient = RegisteredClient
        .withId(UUID.randomUUID().toString())
        .clientId("client01")
        .clientSecret("{noop}secret01")
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
        .redirectUri("http://127.0.0.1:8080/login/oauth2/code/client01")
        .redirectUri("http://127.0.0.1:8080/authorized")
        .scope(OidcScopes.OPENID)
        .scope("message.read")
        .scope("message.write")
        .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
        .build();

    RegisteredClient registeredClient2 = RegisteredClient
        .withId(UUID.randomUUID().toString())
        .clientId("client02")
        .clientSecret("{noop}secret02")
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
        .authorizationGrantType(AuthorizationGrantType.PASSWORD)
        .scope("read")
        .scope("write")
        .build();

    return new InMemoryRegisteredClientRepository(registeredClient, registeredClient2);
  }

  @Bean
  public JWKSource<SecurityContext> jwkSource() {
    KeyPair keyPair = KeyGenerators.generateRsaKey();
    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
    RSAKey rsaKey = new RSAKey.Builder(publicKey)
        .privateKey(privateKey)
        .keyID(UUID.randomUUID().toString())
        .build();
    JWKSet jwkSet = new JWKSet(rsaKey);
    return new ImmutableJWKSet<>(jwkSet);
  }

  @Bean
  public ProviderSettings providerSettings() {
    return ProviderSettings.builder()
//        .issuer("http://localhost:9000")
        .build();
  }


}
