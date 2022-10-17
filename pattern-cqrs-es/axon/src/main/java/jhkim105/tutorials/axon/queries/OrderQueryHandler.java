package jhkim105.tutorials.axon.queries;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderQueryHandler {

  private final OrderRepository orderRepository;

  @QueryHandler
  public List<Order> handle(FindAllOrdersQuery query) {
    log.info("[QueryHandler] {}", query);
    return orderRepository.findAll();
  }
}
