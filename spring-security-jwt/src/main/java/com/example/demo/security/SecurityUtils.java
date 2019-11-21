package com.example.demo.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {
  private SecurityUtils() {
  }

  public static String getCurrentUsername() {
    return getAuthUser().getUsername();
  }

  public static AuthUser getAuthUser() {
    SecurityContext ctx = SecurityContextHolder.getContext();
    Authentication authentication = ctx.getAuthentication();
    if (authentication == null)
      throw new AccessDeniedException("authentication not exists.");
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    if (userDetails == null) {
      throw new AccessDeniedException("userDetails not exists.");
    }
    return (AuthUser)userDetails;
  }

  public static AuthUser getCurrentAuthUserSilently() {
    try {
      return getAuthUser();
    } catch (AccessDeniedException ae) {
      // ignored
    }
    return null;
  }
}
