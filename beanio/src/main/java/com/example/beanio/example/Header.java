package com.example.beanio.example;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class Header implements Serializable {

  private static final long serialVersionUID = 1833194287730628681L;
  private int size;
  private String token;

  @Builder
  public Header(int size, String token) {
    this.size = size;
    this.token = token;
  }
}
