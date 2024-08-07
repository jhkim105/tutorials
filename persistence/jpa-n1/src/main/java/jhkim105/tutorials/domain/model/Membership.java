package jhkim105.tutorials.domain.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "t_membership")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Membership {
  @Id
  @UuidGenerator
  @Column(length = 50)
  private String id;

  @OneToOne(optional = false)
  private User user;

  @Column(nullable = false)
  private String name;

  private Instant expireAt;

}
