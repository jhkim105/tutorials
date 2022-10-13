package jhkim105.tutorials.axon.commands;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OrderCreatedEvent {
  private final String orderId;
}
