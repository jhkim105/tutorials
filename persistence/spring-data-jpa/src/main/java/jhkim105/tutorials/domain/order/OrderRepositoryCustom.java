package jhkim105.tutorials.domain.order;

import java.util.List;

public interface OrderRepositoryCustom {

  void update();

  List<OrderProjection> getOrderProjections();

}
