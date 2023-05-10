package jhkim105.tutorials.multitenancy.master.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;

@Entity
@Getter
@Setter
@ToString
@Audited
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false, of = "id")
public class Tenant {

  public static final String DEFAULT_TENANT_ID = "default";
  public static final String DATABASE_NAME_PREFIX = "demo_multitenancy_";

  @Id
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @GeneratedValue(generator = "uuid")
  @Column(length = 50)
  private String id;

  @Column(unique = true)
  private String name;

  private String dbName;
  private String dbAddress;
  private String dbUsername;
  private String dbPassword;
  private int maxTotal = 10;
  private int maxIdle = 10;
  private int minIdle = 0;
  private int initialSize = 0;

  @Builder
  public Tenant(String name, String dbAddress, String dbUsername, String dbPassword) {
    this.name = name;
    this.dbName = String.format("%s%s", DATABASE_NAME_PREFIX, name);
    this.dbAddress = dbAddress;
    this.dbUsername = dbUsername;
    this.dbPassword = dbPassword;
  }

  @Transient
  public String getJdbcUrl() {
    return String.format("jdbc:mariadb://%s/%s?createDatabaseIfNotExist=true", dbAddress, dbName);
  }

}
