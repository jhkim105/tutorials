package jhkim105.tutorials.authorization_server_jpa;

import static org.assertj.core.api.Assertions.assertThat;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.IOException;
import java.util.UUID;
import jhkim105.tutorials.authorization_server_jpa.service.JpaRegisteredClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
class AuthorizationCodeGrantTypeTests {

  @LocalServerPort
  private int port;

  @Autowired
  private WebClient webClient;

  @Autowired
  private JpaRegisteredClientRepository jpaRegisteredClientRepository;

  private static final String REDIRECT_URI = "http://127.0.0.1:8080/login/oauth2/code/client01";

  private static final String AUTHORIZATION_REQUEST = UriComponentsBuilder
      .fromPath("/oauth2/authorize")
      .queryParam("response_type", "code")
      .queryParam("client_id", "client01")
      .queryParam("scope", "openid")
      .queryParam("state", "some-state")
      .queryParam("redirect_uri", REDIRECT_URI)
      .toUriString();


  RegisteredClient registeredClient;

  @BeforeEach
  void createClient() {
    registeredClient = RegisteredClient
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
    jpaRegisteredClientRepository.save(registeredClient);
  }

  @BeforeEach
  public void setUp() {
    log.info("AUTHORIZATION_REQUEST: {}", AUTHORIZATION_REQUEST);
    this.webClient.getOptions().setThrowExceptionOnFailingStatusCode(true);
    this.webClient.getOptions().setRedirectEnabled(true);
    this.webClient.getCookieManager().clearCookies();	// log out
  }

  @AfterEach
  void deleteClient() {
    jpaRegisteredClientRepository.delete(registeredClient);
  }

  @Test
  void whenLoginSuccessfulThenDisplayNotFoundError() throws IOException {
    HtmlPage page = this.webClient.getPage("/");

    assertLoginPage(page);

    this.webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
    WebResponse signInResponse = signIn(page, "user01", "pass01").getWebResponse();
    assertThat(signInResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());	// there is no "default" index page
  }

  @Test
  public void whenLoginFailsThenDisplayBadCredentials() throws IOException {
    HtmlPage page = this.webClient.getPage("/");

    HtmlPage loginErrorPage = signIn(page, "user1", "wrong-password");

    HtmlElement alert = loginErrorPage.querySelector("div[role=\"alert\"]");
    assertThat(alert).isNotNull();
    assertThat(alert.getTextContent()).isEqualTo("Bad credentials");
  }

  @Test
  public void whenNotLoginAndRequestingTokenThenRedirectsToLogin() throws IOException {
    HtmlPage page = this.webClient.getPage(AUTHORIZATION_REQUEST);

    assertLoginPage(page);
  }

  @Test
  public void whenLoginAndRequestingTokenThenRedirectsToClientApplication() throws IOException {
    // Log in
    this.webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
    this.webClient.getOptions().setRedirectEnabled(false);
    signIn(this.webClient.getPage("/login"), "user01", "pass01");

    // Request token
    WebResponse response = this.webClient.getPage(AUTHORIZATION_REQUEST).getWebResponse();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.MOVED_PERMANENTLY.value());
    String location = response.getResponseHeaderValue("location");
    assertThat(location).startsWith(REDIRECT_URI);
    assertThat(location).contains("code=");
  }

  private static <P extends Page> P signIn(HtmlPage page, String username, String password) throws IOException {
    HtmlInput usernameInput = page.querySelector("input[name=\"username\"]");
    HtmlInput passwordInput = page.querySelector("input[name=\"password\"]");
    HtmlButton signInButton = page.querySelector("button");

    usernameInput.type(username);
    passwordInput.type(password);
    return signInButton.click();
  }

  private static void assertLoginPage(HtmlPage page) {
    assertThat(page.getUrl().toString()).endsWith("/login");

    HtmlInput usernameInput = page.querySelector("input[name=\"username\"]");
    HtmlInput passwordInput = page.querySelector("input[name=\"password\"]");
    HtmlButton signInButton = page.querySelector("button");

    assertThat(usernameInput).isNotNull();
    assertThat(passwordInput).isNotNull();
    assertThat(signInButton.getTextContent()).isEqualTo("Sign in");
  }

}
