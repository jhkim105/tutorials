package com.example.beanio.example;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class Message {

  private Header header;

  private Body body;

  @Builder
  public Message(Header header, Body body) {
    this.header = header;
    this.body = body;
  }
}


