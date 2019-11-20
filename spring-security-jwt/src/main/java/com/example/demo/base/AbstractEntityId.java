package com.example.demo.base;

import java.io.Serializable;

public abstract class AbstractEntityId implements Serializable {

  private static final long serialVersionUID = -2053668818011263946L;

    public abstract String toString();

    public abstract boolean equals(Object o);

    public abstract int hashCode();

}
