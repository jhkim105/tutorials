package jhkim105.tutorials.oauth2test;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@Disabled
@Slf4j
class OAuth2AuthorizationCodeTests {



  @Test
  void accessToken() {
    String accessToken = obtainAccessToken("read");
    log.info(accessToken);
  }

  private String obtainAccessToken(String scopes) {
    String redirectUrl = "http://localhost:8080";
    String authorizationUrlFormat = "http://localhost:8089/realms/demo/protocol/openid-connect/auth?response_type=code&client_id=%s&scope=%s&redirect_uri=" + redirectUrl;
    String tokenUrl = "http://localhost:8089/realms/demo/protocol/openid-connect/token";
    String clientId = "oidc-demo";
    String clientSecret = "Muo0SyBXyd3z06G5YPuP4n4gggX8pQlt";
    String username01 = "user01";
    String password01 = "pass01";

    // obtain authentication url with custom codes
    String authorizationUrl = String.format(authorizationUrlFormat, clientId, scopes);
    Response response = RestAssured.given()
        .redirects()
        .follow(false)
        .get(authorizationUrl);
    String authSessionId = response.getCookie("AUTH_SESSION_ID");
    String postAuthenticationUrl = response.asString()
        .split("action=\"")[1].split("\"")[0].replace("&amp;", "&");

    // obtain authentication code and state
    response = RestAssured.given()
        .redirects()
        .follow(false)
        .cookie("AUTH_SESSION_ID", authSessionId)
        .formParams("username", username01, "password", password01, "credentialId", "")
        .post(postAuthenticationUrl);
    assertThat(HttpStatus.FOUND.value()).isEqualTo(response.getStatusCode());

    // extract authorization code
    String location = response.getHeader(HttpHeaders.LOCATION);
    String code = location.split("code=")[1].split("&")[0];

    // get access token
    Map<String, String> params = new HashMap<String, String>();
    params.put("grant_type", "authorization_code");
    params.put("code", code);
    params.put("client_id", clientId);
    params.put("redirect_uri", redirectUrl);
    params.put("client_secret", clientSecret);
    response = RestAssured.given()
        .formParams(params)
        .post(tokenUrl);
    return response.jsonPath()
        .getString("access_token");
  }
}
