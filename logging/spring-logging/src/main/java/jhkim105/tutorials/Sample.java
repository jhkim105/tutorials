package jhkim105.tutorials;

import java.util.UUID;

public record Sample(
    String id,
    String name
) {

  public static Sample of(String name) {
    return new Sample(UUID.randomUUID().toString(), name);
  }

}
