package jhkim105.tutorials.authorization_server_jpa.domain;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "`client`")
public class Client {

  @Id
  private String id;
  private String clientId;
  private Instant clientIdIssuedAt;
  private String clientSecret;
  private Instant clientSecretExpiresAt;
  private String clientName;
  @Column(length = 1000)
  private String clientAuthenticationMethods;
  @Column(length = 1000)
  private String authorizationGrantTypes;
  @Column(length = 1000)
  private String redirectUris;
  @Column(length = 1000)
  private String scopes;
  @Column(length = 2000)
  private String clientSettings;
  @Column(length = 2000)
  private String tokenSettings;

}