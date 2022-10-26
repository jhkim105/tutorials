package com.example.google.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.google.user.User;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
public class AuthenticationTokenUtil {

  private static final String CLAIM_USER_ID = "ui";

  private static final String CLAIM_USERNAME = "un";

  private static final String CLAIM_SCOPES = "sc";

  private final JWTVerifier jwtVerifier;

  private final Algorithm algorithm;

  private final int expiry;

  public AuthenticationTokenUtil(String secret, int expiry) {
    this.algorithm = Algorithm.HMAC256(secret);
    this.jwtVerifier = JWT.require(algorithm).build();
    this.expiry = expiry;
  }

  public String generateToken(AuthUser authUser) {
    Date now = new Date();
    JWTCreator.Builder builder = JWT.create()
        .withClaim(CLAIM_USER_ID, authUser.getUserId())
        .withClaim(CLAIM_USERNAME, authUser.getUsername())
        .withClaim(CLAIM_SCOPES, authUser.getScopes())
        .withIssuedAt(now);

    if (this.expiry != 0)
      builder.withExpiresAt(DateUtils.addSeconds(now, expiry));

    String token = builder.sign(algorithm);
    log.debug("token:{}", token);
    return token;
  }

  public String generateToken(String userId, String username, List<String> scopes) {
    AuthUser authUser = AuthUser.builder()
        .userId(userId)
        .username(username)
        .scopes(scopes)
        .build();

    return generateToken(authUser);
  }

  public void checkToken(String token) {
    jwtVerifier.verify(token);
  }

  public AuthUser parseToken(String token) {
    checkToken(token);
    DecodedJWT jwt = JWT.decode(token);
    String id = jwt.getClaim(CLAIM_USER_ID).asString();
    String username = jwt.getClaim(CLAIM_USERNAME).asString();
    List<String> scopes = jwt.getClaim(CLAIM_SCOPES).asList(String.class);

    AuthUser authUser = AuthUser.builder()
        .userId(id)
        .username(username)
        .scopes(scopes)
        .build();

    return authUser;
  }

  public String getUsernameFromToken(String token) {
    return parseToken(token).getUsername();
  }

  public boolean validateToken(String token, UserDetails userDetails) {
    final String username = getUsernameFromToken(token);
    log.debug("userDetails->{}", userDetails);
    log.debug("token.username:{}, userDetails.username:{}", username, userDetails.getUsername());
    try {
      return username.equals(userDetails.getUsername());
    } catch(JWTVerificationException e) {
      return false;
    }
  }

  @Getter
  @ToString
  @Builder
  public static class AuthUser implements Principal {
    private String userId;
    private String username;
    private List<String> scopes;

    public static AuthUser of(User user) {
      AuthUser authUser = AuthUser.builder()
          .userId(user.getId())
          .username(user.getEmail())
          .scopes(user.getAuthorities().stream().map(Authority::name).collect(Collectors.toList()))
          .build();
      return authUser;
    }

    @Override
    public String getName() {
      return userId;
    }
  }
}
