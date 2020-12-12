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
public class Body implements Serializable {

  private static final long serialVersionUID = -4164543352083297796L;
  private int number;
  private String code;
  private String msg;

  @Builder
  public Body(int number, String code, String msg) {
    this.number = number;
    this.code = code;
    this.msg = msg;
  }
}
