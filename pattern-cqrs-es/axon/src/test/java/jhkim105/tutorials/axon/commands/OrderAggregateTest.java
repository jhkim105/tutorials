package jhkim105.tutorials.axon.commands;

import java.util.UUID;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderAggregateTest {

  private static final String ORDER_ID = UUID.randomUUID().toString();

  private FixtureConfiguration<OrderAggregate> fixture;

  @BeforeEach
  void setUp() {
    fixture = new AggregateTestFixture<>(OrderAggregate.class);
  }

  @Test
  void createOrderCommand() {
    fixture.givenNoPriorActivity()
        .when(new CreateOrderCommand(ORDER_ID))
        .expectEvents(new OrderCreatedEvent(ORDER_ID));
  }


}