package jhkim105.tutorials.multitenancy.controller;

import jhkim105.tutorials.multitenancy.domain.User;
import jhkim105.tutorials.multitenancy.master.domain.Tenant;
import jhkim105.tutorials.multitenancy.master.service.TenantService;
import jhkim105.tutorials.multitenancy.service.UserService;
import jhkim105.tutorials.multitenancy.tenant.TenantContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/join")
@Slf4j
public class UserJoinController {

  private final UserService userService;
  private final TenantService tenantService;


  @PostMapping
  public ResponseEntity<User> join(String username) {
    Tenant tenant = tenantService.createTenant(username);
    TenantContextHolder.setTenantId(tenant.getId());
    User user = userService.join(username);
    log.info("{}", tenantService.findById(tenant.getId()));
    log.info("{}", userService.findById(user.getId()));
    return ResponseEntity.ok(user);
  }


}
