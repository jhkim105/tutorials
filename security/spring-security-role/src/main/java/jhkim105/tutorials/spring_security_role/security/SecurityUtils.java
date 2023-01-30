package jhkim105.tutorials.spring_security_role.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {
  private SecurityUtils() {
  }
  public static UserDetails getCurrentUserDetails() {
    SecurityContext ctx = SecurityContextHolder.getContext();
    Authentication authentication = ctx.getAuthentication();
    if (authentication == null)
      throw new AccessDeniedException("authentication not exists.");
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    if (userDetails == null) {
      throw new AccessDeniedException("userDetails not exists.");
    }
    return userDetails;
  }

  public static UserDetails getCurrentUserDetailsSilently() {
    try {
      return getCurrentUserDetails();
    } catch (AccessDeniedException ae) {
      // ignored
    }
    return null;
  }
}
