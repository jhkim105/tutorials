package jhkim105.tutorials.axon.commands;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@RequiredArgsConstructor
@Getter
public class CreateOrderCommand {

  @TargetAggregateIdentifier
  private final String orderId;

}
