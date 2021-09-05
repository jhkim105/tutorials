package jhkim105.tutorials.spring.data.jpa.nplusone.domain;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "kp_product")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Product implements Serializable {

  private static final long serialVersionUID = 8896708930985157915L;

  @Id
  @GeneratedValue
  private Long id;

  @Column(length = 200, nullable = false)
  private String title;

  @Column(nullable = false)
  private long targetAmount;

  @Column(nullable = false)
  private long currentAmount;

  @Column(nullable = false)
  private long currentInvestorCount;

  @Enumerated(value = EnumType.STRING)
  @Column(length = ProductType.length, nullable = false)
  private ProductType type;

  @Column(nullable = false)
  private LocalDateTime startedAt;

  @Column(nullable = false)
  private LocalDateTime finishedAt;

  @OneToMany(mappedBy = "product")
  private Set<Invest> invests = new HashSet<>();

  @Builder
  public Product(Long id, ProductType type, String title, Long targetAmount, LocalDateTime startedAt, LocalDateTime finishedAt) {
    this.id = id;
    this.type = type;
    this.title = title;
    this.targetAmount = targetAmount;
    this.startedAt = startedAt;
    this.finishedAt = finishedAt;
  }

  public void updateByInvest(Long amount) {
    this.currentAmount += amount;
    this.currentInvestorCount++;
  }

  public enum ProductType {
    REAL_ESTATE, CREDIT;
    public final static int length = 20;
  }
}
