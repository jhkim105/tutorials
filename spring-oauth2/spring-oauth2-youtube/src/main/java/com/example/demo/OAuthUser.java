package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "su_oauth_user",
    uniqueConstraints = { @UniqueConstraint(columnNames = {"oauth_provider", "user_id"}) })
@Getter
@EqualsAndHashCode(callSuper = false, of = {"id"})
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id")
public class OAuthUser {

  @Id
  @Column(length = 50)
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  @GeneratedValue(generator = "system-uuid")
  private String id;

  @Column(name = "oauth_provider", length = 50)
  @Enumerated(EnumType.STRING)
  private OAuthProvider oauthProvider;

  @Column(name = "user_id", length = 100)
  private String userId;

  private String email;

  @Column(name = "access_token")
  private String accessToken;

  @Column(name = "refresh_token")
  private String refreshToken;

  @Column(name = "expire_date")
  private LocalDateTime expireDate;

  @Builder
  public OAuthUser(OAuthProvider oauthProvider, String userId, String email, String accessToken, int expiresIn, String refreshToken) {
    this.oauthProvider = oauthProvider;
    this.userId = userId;
    this.email = email;
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    updateAccessToken(accessToken, expiresIn);
  }

  public void updateAccessToken(String accessToken, long expiresIn) {
    this.expireDate = LocalDateTime.now().plus(expiresIn, ChronoUnit.SECONDS);
    this.accessToken = accessToken;
  }

  public boolean isAccessTokenExpired() {
    if (expireDate == null) {
      return true;
    }

    if (expireDate.isBefore(LocalDateTime.now())) {
      return true;
    }

    return false;
  }

  public enum OAuthProvider {
    GOOGLE
  }
}
