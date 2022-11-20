package jhkim105.tutorials.spring.cloud.micrservices.order.api_client;

import java.io.Serializable;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Product implements Serializable {

  private static final long serialVersionUID = -1773662851631418493L;

  private String id;
  private String name;


}
