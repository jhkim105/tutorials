package com.example.demo.user;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class LoginRequest {
  @NonNull
  private String username;

  @NonNull
  private String password;
}
