package com.example.multitenant.master;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.Hibernate;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tenant {
  @Id
  @Column(length = 50)
  private String id;

  private String dbName;

  private String dbAddress;

  private String dbUsername;

  private String dbPassword;

  @Transient
  public String getJdbcUrl() {
    return String.format("jdbc:mariadb://%s/%s?createDatabaseIfNotExist=true", dbAddress, dbName);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Tenant tenant = (Tenant) o;

    return Objects.equals(id, tenant.id);
  }

  @Override
  public int hashCode() {
    return 2096421156;
  }
}