package jhkim105.tutorials.multitenancy.master.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;

@Entity
@Getter
@ToString
@Audited
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tenant {

  public static final String DEFAULT_TENANT_ID = "default";
  public static final String DATABASE_NAME_PREFIX = "demo_multitenancy_";

  @Id
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @GeneratedValue(generator = "uuid")
  @Column(length = 50)
  private String id;

  @Column(unique = true)
  @Setter
  private String name;

  private String dbName;
  private String dbAddress;

  private String dbUsername;

  private String dbPassword;

  @ColumnDefault("10")
  private int maxTotal = 10;

  @ColumnDefault("10")
  private int maxIdle = 10;

  @ColumnDefault("0")
  private int minIdle = 0;

  @ColumnDefault("0")
  private int initialSize = 0;

  @Builder
  public Tenant(String name, String dbAddress, String dbUsername, String dbPassword) {
    this.name = name;
    this.dbName = name;
    this.dbAddress = dbAddress;
    this.dbUsername = dbUsername;
    this.dbPassword = dbPassword;
  }

  @Transient
  public String getJdbcUrl() {
    return String.format("jdbc:mariadb://%s/%s?createDatabaseIfNotExist=true", dbAddress, getDatabaseName());
  }

  @Transient
  public String getDatabaseName() {
    return String.format("%s%s", DATABASE_NAME_PREFIX, dbName);
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
