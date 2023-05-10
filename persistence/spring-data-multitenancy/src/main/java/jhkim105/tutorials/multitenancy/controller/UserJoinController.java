package jhkim105.tutorials.multitenancy.controller;

import jhkim105.tutorials.multitenancy.master.domain.Tenant;
import jhkim105.tutorials.multitenancy.master.service.TenantService;
import jhkim105.tutorials.multitenancy.tenant.context.TenantContext;
import jhkim105.tutorials.multitenancy.tenant.domain.User;
import jhkim105.tutorials.multitenancy.tenant.service.UserService;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-join")
@Slf4j
public class UserJoinController {

  private final UserService userService;
  private final TenantService tenantService;


  @PostMapping
  public ResponseEntity<User> join(@RequestBody UserJoinRequest userJoinRequest) {
    String tenantName = userJoinRequest.getTenantName();
    String username = userJoinRequest.getUsername();
    User user;
    if (!StringUtils.isBlank(tenantName)) {
      Tenant tenant = tenantService.createTenant(tenantName);
      TenantContext.setTenantId(tenant.getId());
      user = userService.join(tenant, username);
    } else {
      user = userService.join(username);
    }

    return ResponseEntity.ok(user);
  }

  @Getter
  public static class UserJoinRequest {
    private final String tenantName;
    private final String username;


    @Builder
    public UserJoinRequest(String tenantName, String username) {
      this.tenantName = tenantName;
      this.username = username;
    }

  }
  
}
