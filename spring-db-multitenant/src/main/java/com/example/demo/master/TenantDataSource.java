package com.example.demo.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TenantDataSource {
  @Id
  @GeneratedValue(generator = "uuid")
  @Column(length = 50)
  private String id;

  private String name;

  private String url;

  private String username;

  private String password;

  private String driverClassName;


}
