package com.example.demo.auth;

import com.example.demo.auth.AuthenticationTokenUtil.AuthUser;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

  private SecurityUtils() {
  }

  public static AuthUser getAuthUser() {
    SecurityContext ctx = SecurityContextHolder.getContext();
    Authentication authentication = ctx.getAuthentication();
    if (authentication == null)
      throw new AccessDeniedException("authentication not exists.");
    if (!(authentication.getPrincipal() instanceof AuthUser))
      throw new AccessDeniedException("Not properly authenticated.");

    AuthUser authUser = (AuthUser) authentication.getPrincipal();
    if (authUser == null) {
      throw new AccessDeniedException("AuthUser not exists.");
    }

    return authUser;
  }

  public static AuthUser getAuthUserSilently() {
    try {
      return getAuthUser();
    } catch (AccessDeniedException ae) {
      // ignored
    }
    return null;
  }
}
