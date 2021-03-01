package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.Column;
import javax.persistence.Entity;
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
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "su_oauth_user",
    uniqueConstraints = { @UniqueConstraint(columnNames = {"registration_id", "user_id"}) })
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

  @Column(name = "registration_id", length = 50)
  private String registrationId;

  @Column(name = "user_id", length = 100)
  private String userId;

  private String email;

  @Column(name = "access_token")
  private String accessToken;

  @Column(name = "refresh_token")
  private String refreshToken;

  @Builder
  public OAuthUser(String registrationId, String userId, String email, String accessToken, String refreshToken) {
    this.registrationId = registrationId;
    this.userId = userId;
    this.email = email;
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }

  public void update(String accessToken, String refreshToken) {
    this.accessToken = accessToken;
    if (StringUtils.isNotBlank(refreshToken)) {
      this.refreshToken = refreshToken;
    }
  }
}
