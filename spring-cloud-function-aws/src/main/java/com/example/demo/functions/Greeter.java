package com.example.demo.functions;

import java.util.function.Function;

public class Greeter implements Function<String, String> {

  @Override
  public String apply(String s) {
    System.out.println("param:" + s);
    return "Hello " + s;
  }
}
