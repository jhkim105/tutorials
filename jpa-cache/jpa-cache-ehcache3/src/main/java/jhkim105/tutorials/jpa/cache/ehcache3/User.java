package jhkim105.tutorials.jpa.cache.ehcache3;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "t_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Cache(region = "user", usage = CacheConcurrencyStrategy.READ_WRITE)
public class User {

  @Id
  @GeneratedValue
  private Long id;

  private String name;


}
