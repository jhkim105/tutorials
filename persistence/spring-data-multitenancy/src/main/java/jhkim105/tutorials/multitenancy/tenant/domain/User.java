package jhkim105.tutorials.multitenancy.tenant.domain;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import jhkim105.tutorials.multitenancy.crypto.StringEncryptConverter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;

@Entity
@Getter
@ToString
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Audited
public class User {

  @Id
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @GeneratedValue(generator = "uuid")
  @Column(length = 50)
  private String id;

  @Column(nullable = false, unique = true)
  @Setter
  private String username;

  @Column(name = "tenant_id")
  private String tenantId;

  @Convert(converter = StringEncryptConverter.class)
  private String secret;

  @Builder
  public User(String tenantId, String username) {
    this.tenantId = tenantId;
    this.username = username;
    this.secret = RandomStringUtils.random(10);
  }

}
