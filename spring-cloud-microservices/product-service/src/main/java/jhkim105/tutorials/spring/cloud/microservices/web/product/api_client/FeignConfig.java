package jhkim105.tutorials.spring.cloud.microservices.web.product.api_client;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

public class FeignConfig {

  public static final String CLIENT_REGISTRATION_ID = "api-client";

  @Bean
  public RequestInterceptor requestInterceptor(ClientRegistrationRepository clientRegistrationRepository, OAuth2AuthorizedClientManager authorizedClientManager) {
    ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(CLIENT_REGISTRATION_ID);
    OAuthClientCredentialsFeignManager clientCredentialsFeignManager =
        new OAuthClientCredentialsFeignManager(authorizedClientManager, clientRegistration);
    return requestTemplate -> {
      requestTemplate.header("Authorization", "Bearer " + clientCredentialsFeignManager.getAccessToken());
    };
  }

  @Bean
  OAuth2AuthorizedClientManager authorizedClientManager(ClientRegistrationRepository clientRegistrationRepository, OAuth2AuthorizedClientService oAuth2AuthorizedClientService) {
    OAuth2AuthorizedClientProvider authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
        .clientCredentials()
        .build();

    AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager =
        new AuthorizedClientServiceOAuth2AuthorizedClientManager(clientRegistrationRepository, oAuth2AuthorizedClientService);
    authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
    return authorizedClientManager;
  }

}
