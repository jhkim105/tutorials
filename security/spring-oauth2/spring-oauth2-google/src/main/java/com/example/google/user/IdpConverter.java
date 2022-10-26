package com.example.google.user;


import org.springframework.core.convert.converter.Converter;

public class IdpConverter implements Converter<String, Idp> {

  @Override
  public Idp convert(String source) {
    return Idp.fromValue(source);
  }
}
