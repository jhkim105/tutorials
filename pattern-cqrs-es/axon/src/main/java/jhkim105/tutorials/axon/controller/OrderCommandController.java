package jhkim105.tutorials.axon.controller;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import jhkim105.tutorials.axon.commands.CreateOrderCommand;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderCommandController {
  private final CommandGateway commandGateway;

  @PostMapping()
  public CompletableFuture<String> createOrder() {
    String oderId = UUID.randomUUID().toString();
    return commandGateway.send(new CreateOrderCommand(oderId));
  }

}
