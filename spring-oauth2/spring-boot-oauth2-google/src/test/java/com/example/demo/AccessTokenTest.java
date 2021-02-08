package com.example.demo;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;

class AccessTokenTest {

//  private static final String CLIENT_ID = "176961205934-eu3f6o0o3ccomln7t9e53qa19lk3hqu1.apps.googleusercontent.com";
  private static final String CLIENT_ID = "78937684060-m7tcaso7dqvfg9hs7mk0jcfopl4d0dvl.apps.googleusercontent.com";

  /**
   * https://developers.google.com/identity/sign-in/web/backend-auth
   */
  @Test
  @Ignore
  void testAccessToken() {
    String accessToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImFmZGU4MGViMWVkZjlmM2JmNDQ4NmRkODc3YzM0YmE0NmFmYmJhMWYiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwiYXpwIjoiMTc2OTYxMjA1OTM0LWV1M2Y2bzBvM2Njb21sbjd0OWU1M3FhMTlsazNocXUxLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiYXVkIjoiMTc2OTYxMjA1OTM0LWV1M2Y2bzBvM2Njb21sbjd0OWU1M3FhMTlsazNocXUxLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwic3ViIjoiMTAxNzU0MjY2MDUxNDk1NDc0ODExIiwiZW1haWwiOiJqaGtpbTEwNUBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiYXRfaGFzaCI6Imx5UXh2T0FrVWswNG5MWWdFZ0ktM3ciLCJuYW1lIjoiSmlod2FuIEtpbSIsInBpY3R1cmUiOiJodHRwczovL2xoNi5nb29nbGV1c2VyY29udGVudC5jb20vLUpWck1fcU1sZ3NVL0FBQUFBQUFBQUFJL0FBQUFBQUFBQUFBL0FDSGkzcmUwZkppSUsxeklhTGNMWXp4YlZsaTZzS01uN2cvczk2LWMvcGhvdG8uanBnIiwiZ2l2ZW5fbmFtZSI6IkppaHdhbiIsImZhbWlseV9uYW1lIjoiS2ltIiwibG9jYWxlIjoia28iLCJpYXQiOjE1NjIyMjM4NDAsImV4cCI6MTU2MjIyNzQ0MCwianRpIjoiODZmOWY1YzA4YTViYzBlZDcxMWZjYzgzMTZkMjMwYWY4MmVjNGY5ZCJ9.USTLEbn-fEMwH3JrkSAA6vt0Cgzp2go1VdD3jWYq4ThbSQiebs8G_0d9CNU8AbRfIrG7rJM7j3J5jUGsQfub10jZY8AUMiwjObfXEsLLEsSKfQIpBQ9rAEY4DiZ2n000ErGgxxcRJbUHVKoASolYanSnFXKtto1EJa-3wmfE1zPN9_zU0_KkF21O7sTWApqPVxtye5zZ3q4jAPpMq4wFGA0t7zGGdEVU1BkuxA_XNmarw91nUq_dctrX0o94uP5kFwGi0SgdbJheqpB1CcNmyb69NzsAsuwNXfAEfsxi_kD8MnlYt2AKjgltNJBPn-tDCLlOuzF4WcdcPnGvG_P09g";
    GoogleIdTokenVerifier verifier
        = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
          .setAudience(Collections.singletonList(CLIENT_ID))
          .build();

    GoogleIdToken idToken = null;
    try {
      idToken = verifier.verify(accessToken);
    } catch (GeneralSecurityException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (idToken != null) {
      Payload payload = idToken.getPayload();

      // Print user identifier
      String userId = payload.getSubject();
      System.out.println("User ID: " + userId);

      // Get profile information from payload
      String email = payload.getEmail();
      boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
      String name = (String) payload.get("name");
      String pictureUrl = (String) payload.get("picture");
      String locale = (String) payload.get("locale");
      String familyName = (String) payload.get("family_name");
      String givenName = (String) payload.get("given_name");

      // Use or store profile information
      // ...

    } else {
      System.out.println("Invalid ID token.");
    }
  }
}
