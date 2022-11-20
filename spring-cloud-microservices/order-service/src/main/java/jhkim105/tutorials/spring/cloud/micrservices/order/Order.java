package jhkim105.tutorials.spring.cloud.micrservices.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class Order {

  private String id;

  private String name;

  private String productId;

  private int quantity;

  @Setter
  private String productName;

  @Builder
  public Order(String id, String name, String productId, int quantity) {
    this.id = id;
    this.name = name;
    this.productId = productId;
    this.quantity = quantity;
  }

}
