package jhkim105.tutorials.kafka;

import java.time.LocalDateTime;

public record SampleMessage(
    String message,
    LocalDateTime createdAt
) {

  public static SampleMessage of(String message) {
    return new SampleMessage(message, LocalDateTime.now());
  }
}
