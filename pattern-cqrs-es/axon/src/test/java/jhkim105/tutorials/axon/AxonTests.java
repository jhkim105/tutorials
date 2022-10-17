package jhkim105.tutorials.axon;

import jhkim105.tutorials.axon.commands.CreateOrderCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AxonTests {

  @Autowired
  private CommandGateway commandGateway;

  @Autowired
  private QueryGateway queryGateway;

  @Autowired
  private EventGateway eventGateway;


  @Test
  void test() {
    commandGateway.send(new CreateOrderCommand("id01"));
  }

}
