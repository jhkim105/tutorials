package jhkim105.tutorials.spring.data.jpa.nplusone.domain;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class InvestProjection {

  private String userId;

  private Long investId;

  private Long productId;

  private String productTitle;

  private Long amount;

  @QueryProjection
  public InvestProjection(String userId, Long investId, Long productId, String productTitle, Long amount) {
    this.userId = userId;
    this.investId = investId;
    this.productId = productId;
    this.productTitle = productTitle;
    this.amount = amount;
  }
}
