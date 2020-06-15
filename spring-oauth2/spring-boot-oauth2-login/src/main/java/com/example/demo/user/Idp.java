package com.example.demo.user;

import java.util.Arrays;

public enum Idp {
  GOOGLE;

  public String getValue() {
    return this.name().toLowerCase();
  }

  public static Idp fromValue(String value) {
    for (Idp idp : values()) {
      if(idp.name().equalsIgnoreCase(value)) {
        return idp;
      }
    }
    throw new IllegalArgumentException(String.format("Unknown value %s, Allowd values are %s",
        value, Arrays.toString(values())));
  }
}
