package com.example.demo.user;

import com.example.demo.auth.Authority;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tu_user")
@Getter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements Serializable {

  private static final long serialVersionUID = 7535937185214543104L;

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  @Column(length = 50)
  private String id;

  private String email;

  private String password;

  private String fullName;

  @ElementCollection(targetClass = Authority.class)
  @JoinTable(name = "tu_user_authorities", joinColumns = {@JoinColumn(name = "user_id")})
  @Column(name = "authority", nullable = false)
  @Enumerated(EnumType.STRING)
  private Set<Authority> authorities = new HashSet<>();

  @Builder
  public User(String email, String password, String fullName, Set<Authority> authorities) {
    this.email = email;
    this.password = password;
    this.fullName = fullName;
    this.authorities = authorities;
  }
}
