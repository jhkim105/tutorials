package jhkim105.tutorials.axon;

import jhkim105.tutorials.axon.commands.CreateOrderCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AxonTests {

  @Autowired
  private CommandGateway commandGateway;

  @Autowired
  private QueryGateway queryGateway;


  @Test
  void test() {
  }

}
