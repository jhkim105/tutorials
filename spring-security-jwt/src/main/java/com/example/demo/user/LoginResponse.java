package com.example.demo.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginResponse {

  private static final long serialVersionUID = 3417144000325211448L;

  private String authToken;

  private String refreshToken;

  @Builder
  public LoginResponse(String authToken, String refreshToken) {
    this.authToken = authToken;
    this.refreshToken = refreshToken;
  }
}
