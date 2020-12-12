package com.example.beanio.example;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.beanio.annotation.Record;

@Getter
@ToString
@Record("message01")
public class Message2 {
  private String code;
  private int number;
  private String value;

  @Builder
  public Message2(String code, int number, String value) {
    this.code = code;
    this.number = number;
    this.value = value;
  }
}
