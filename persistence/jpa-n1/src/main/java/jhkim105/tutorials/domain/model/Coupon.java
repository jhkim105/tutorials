package jhkim105.tutorials.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "t_coupon")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Coupon {

  @Id
  @GeneratedValue
  private Long id;

  private String name;

  @ManyToOne
  private User user;

}
