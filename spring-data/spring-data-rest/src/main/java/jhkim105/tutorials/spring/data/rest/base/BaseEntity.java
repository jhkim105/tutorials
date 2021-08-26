package jhkim105.tutorials.spring.data.rest.base;

import java.io.Serializable;


public abstract class BaseEntity<PK extends Serializable> implements Serializable {

    public abstract String toString();

    public abstract boolean equals(Object o);

    public abstract int hashCode();

    public abstract PK getId();

}

