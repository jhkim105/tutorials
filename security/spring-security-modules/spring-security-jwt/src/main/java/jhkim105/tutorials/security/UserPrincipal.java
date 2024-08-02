package jhkim105.tutorials.security;

import java.util.stream.Collectors;
import jhkim105.tutorials.user.Role;
import jhkim105.tutorials.user.User;

public record UserPrincipal(String id, String authority) {

  public static final String AUTHORITY_SEPARATOR = ",";

  public UserPrincipal(User user) {
    this(
        user.getId(),
        user.getRoles().stream().map(Role::name).collect(Collectors.joining(AUTHORITY_SEPARATOR))
    );
  }
}