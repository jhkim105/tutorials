package jhkim105.tutorials.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
  private SecurityUtils() {
  }
  public static UserPrincipal getAuthUser() {
    SecurityContext ctx = SecurityContextHolder.getContext();
    Authentication authentication = ctx.getAuthentication();
    if (authentication == null)
      throw new AccessDeniedException("Authentication not exists.");

    var principal = authentication.getPrincipal();
    if (principal instanceof UserPrincipal) {
      return (UserPrincipal)principal;
    }

    throw new AccessDeniedException("Not properly authenticated.");
  }

  public static UserPrincipal getAuthUserSilently() {
    try {
      return getAuthUser();
    } catch (AccessDeniedException ae) {
      // ignored
    }
    return null;
  }
}
