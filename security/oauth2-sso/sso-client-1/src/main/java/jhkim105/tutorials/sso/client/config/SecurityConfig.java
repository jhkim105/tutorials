package jhkim105.tutorials.sso.client.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final ClientRegistrationRepository clientRegistrationRepository;
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(authorize -> authorize
        .requestMatchers("/", "/login")
        .permitAll()
        .anyRequest()
        .authenticated())
        .oauth2Login();
    http.logout(logout -> logout.logoutSuccessHandler(oidcLogoutSuccessHandler()));
    return http.build();
  }

  private OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler() {
    OidcClientInitiatedLogoutSuccessHandler successHandler = new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
    successHandler.setPostLogoutRedirectUri("{baseUrl}");
    return successHandler;
  }
  @Bean
  WebClient webClient(ClientRegistrationRepository clientRegistrationRepository,
      OAuth2AuthorizedClientRepository authorizedClientRepository) {
    ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 =
        new ServletOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrationRepository,
            authorizedClientRepository);
    oauth2.setDefaultOAuth2AuthorizedClient(true);
    return WebClient.builder()
        .apply(oauth2.oauth2Configuration())
        .build();
  }

}
