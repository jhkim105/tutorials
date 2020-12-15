package com.example.beanio.protocol;

import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class Data {
  private String kind;
  private Map<String, Object> body = new HashMap<>();

  @Builder
  public Data(String kind, int number1, String data1) {
    this.kind = kind;
    body.put("number1", number1);
    body.put("data1", data1);
  }

}
