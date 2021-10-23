package jhkim105.tutorials.jpa.cache.redisson;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Table(name = "t_order")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Order {

  @Id
  @GeneratedValue
  private Long id;

  private String name;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private User user;

}
