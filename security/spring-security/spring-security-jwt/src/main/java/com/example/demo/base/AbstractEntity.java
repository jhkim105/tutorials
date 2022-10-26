package com.example.demo.base;

import java.io.Serializable;

public abstract class AbstractEntity<K extends Serializable> implements Serializable {
  private static final long serialVersionUID = 5534003885658026617L;
  public abstract String toString();
  public abstract K getId();
}
