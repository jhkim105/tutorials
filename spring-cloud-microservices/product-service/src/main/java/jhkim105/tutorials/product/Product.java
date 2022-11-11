package jhkim105.tutorials.product;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Product implements Serializable {

  private static final long serialVersionUID = -1773662851631418493L;

  private String id;
  private String name;

  @Builder
  public Product(String id, String name) {
    this.id = id;
    this.name = name;
  }
}
