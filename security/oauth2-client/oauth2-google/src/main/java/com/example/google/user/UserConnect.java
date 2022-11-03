package com.example.google.user;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tu_user_connect")
@Getter @Setter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserConnect implements Serializable {

  private static final long serialVersionUID = -9118057470356805740L;

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  @Column(length = 50)
  private String id;

  private Idp idp;

  @Column(name = "access_token", length = 512)
  private String accessToken;

  @ManyToOne(optional = false)
  private User user;

  @Builder
  public UserConnect(User user, Idp idp, String accessToken) {
    this.user = user;
    this.idp = idp;
    this.accessToken = accessToken;
  }
}
