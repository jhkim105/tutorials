package jhkim105.tutorials.gateway_oauth2.resource_server;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

  private String id;
  private String name;

  @Builder
  public Product(String id, String name) {
    this.id = id;
    this.name = name;
  }


}
