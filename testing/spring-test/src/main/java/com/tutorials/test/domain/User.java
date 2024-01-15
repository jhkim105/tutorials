package com.tutorials.test.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "users")
@Getter
@ToString
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

  @Id
  @UuidGenerator
  @Column(length = 50)
  private String id;

  @Column(nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public static User createForTest(String username, String password) {
    var user = new User(username, password);
    user.id = UUID.randomUUID().toString();
    return user;
  }



}
