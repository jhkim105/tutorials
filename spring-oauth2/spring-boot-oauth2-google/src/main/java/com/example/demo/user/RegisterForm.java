package com.example.demo.user;

import com.example.demo.auth.Authority;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RegisterForm {
  private String email;

  private String fullName;

  private String password;

  private String confirmPassword;

  private Idp idp;

  private String accessToken;

  public User toUser() {
    // @formatter:off
    User user = User.builder()
        .email(this.email)
        .password(this.password)
        .authorities(Authority.getDefaultAuthorites())
        .build();

    return user;
    // @formatter:on
  }

}
