package jhkim105.tutorials.axon.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import jhkim105.tutorials.axon.queries.FindAllOrdersQuery;
import jhkim105.tutorials.axon.queries.Order;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderQueryController {
  private final QueryGateway queryGateway;

  @GetMapping
  public CompletableFuture<List<OrderResponse>> findAll() {
    return queryGateway.query(new FindAllOrdersQuery(), ResponseTypes.multipleInstancesOf(Order.class))
        .thenApply(r -> r.stream()
            .map(OrderResponse::new)
            .collect(Collectors.toList()));
  }

}
