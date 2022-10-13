package jhkim105.tutorials.axon.queries;

import java.util.HashMap;
import java.util.Map;
import jhkim105.tutorials.axon.commands.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@ProcessingGroup("orders")
public class OrderEventHandler {
  private final Map<String, Order> orders = new HashMap<>();
  private final QueryUpdateEmitter emitter;

  @EventHandler
  public void on(OrderCreatedEvent event) {
    String orderId = event.getOrderId();
    orders.put(orderId, new Order(orderId));
  }

}
