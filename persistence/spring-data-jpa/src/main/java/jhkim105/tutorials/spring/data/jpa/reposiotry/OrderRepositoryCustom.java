package jhkim105.tutorials.spring.data.jpa.reposiotry;

import java.util.List;
import jhkim105.tutorials.spring.data.jpa.projection.OrderProjection;

public interface OrderRepositoryCustom {

  void update();

  List<OrderProjection> getOrderProjections();

}
