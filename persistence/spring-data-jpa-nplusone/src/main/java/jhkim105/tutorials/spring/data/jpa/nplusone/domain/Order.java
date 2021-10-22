package jhkim105.tutorials.spring.data.jpa.nplusone.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;


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

  @ManyToOne(optional = false)
//  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private User user;

  @Column(nullable = false)
  @ColumnDefault("0")
  private boolean completed;

  public void complete() {
    this.completed = true;
  }
}
