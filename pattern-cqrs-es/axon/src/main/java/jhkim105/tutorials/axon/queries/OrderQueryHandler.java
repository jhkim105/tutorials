package jhkim105.tutorials.axon.queries;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderQueryHandler {

  private final OrderRepository orderRepository;

  @QueryHandler
  public List<Order> handle(FindAllOrdersQuery query) {
    return orderRepository.findAll();
  }
}
