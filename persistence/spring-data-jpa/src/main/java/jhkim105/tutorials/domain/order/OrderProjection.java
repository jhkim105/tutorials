package jhkim105.tutorials.domain.order;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OrderProjection {

  private String name;

  private String userName;

  @QueryProjection
  public OrderProjection(String name, String userName) {
    this.name = name;
    this.userName = userName;
  }
}
