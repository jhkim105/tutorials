package jhkim105.tutorials.axon.commands;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class OrderAggregate {

  @AggregateIdentifier
  private String orderId;
  private boolean orderConfirmed;

  @CommandHandler
  public OrderAggregate(CreateOrderCommand command) {
    log.info("CreateOrderCommand:: {}", command);
    AggregateLifecycle.apply(new OrderCreatedEvent(command.getOrderId()));
  }

  @EventSourcingHandler
  public void on(OrderCreatedEvent event) {
    log.info("OrderCreatedEvent:: {}", event);
    this.orderId = event.getOrderId();
  }


}
