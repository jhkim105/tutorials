package com.example.demo.user;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter @Setter
@RequiredArgsConstructor
public class LoginRequest {
  @NonNull
  private String username;

  @NonNull
  private String password;


  public UsernamePasswordAuthenticationToken toUsernamePasswordAuthenticationToken() {
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(this.username, this.password);
    return token;
  }
}
