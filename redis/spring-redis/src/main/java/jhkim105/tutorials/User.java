package jhkim105.tutorials;

import java.io.Serializable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class User implements Serializable {
  private static final long serialVersionUID = -3167515987010511666L;
  private final String name;
}
