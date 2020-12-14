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
public class Message {

//  private static final long serialVersionUID = -3894497903821668443L;
  private Header header;

  private Body body;

  @Builder
  public Message(Header header, Body body) {
    this.header = header;
    this.body = body;
  }
}


