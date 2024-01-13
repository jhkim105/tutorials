package jhkim105.tutorials.testing.contextcaching;

import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class MessageCache {

  private Set<String> messages = new HashSet<>();

  public void add(String message) {
    messages.add(message);
  }

  public String getMessages() {
    return String.join(",", messages);
  }

}
