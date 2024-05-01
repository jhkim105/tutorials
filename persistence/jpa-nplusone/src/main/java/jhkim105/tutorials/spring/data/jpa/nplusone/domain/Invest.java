package jhkim105.tutorials.spring.data.jpa.nplusone.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "kp_invest")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Invest implements Serializable {

  private static final long serialVersionUID = 8916961168463162250L;

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private String userId;

  @ManyToOne(optional = false)
  @JsonIgnore
  @Exclude
  private Product product;

  @Column(nullable = false)
  private Long amount;

  @CreatedDate
  @Column(nullable = false)
  private LocalDateTime investAt;

  @Builder
  public Invest(Long id, String userId, Product product, Long amount) {
    this.id = id;
    this.userId = userId;
    this.product = product;
    this.amount = amount;
    this.investAt = LocalDateTime.now();
  }

  @Transient
  @JsonProperty
  public Long getProductId() {
    return this.product.getId();
  }

  @Transient
  @JsonProperty
  public String getProductTitle() {
    return this.product.getTitle();
  }

  @Transient
  @JsonProperty
  public Long getTargetAmount() {
    return this.product.getTargetAmount();
  }


}
