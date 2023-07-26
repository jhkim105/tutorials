package jhkim105.tutorials.resource_server.jwt;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {ProductController.class, UserInfoController.class})
class ControllerUnitTests {

  @Autowired
  MockMvc mockMvc;

  @Test
  void givenRequestIsAnonymous_thenUnauthorized() throws Exception {
    mockMvc.perform(get("/products")
            .with(anonymous()))
        .andExpect(status().isUnauthorized());
  }

  @Test
    // Role, Authority check 안됨
  void givenUserIsAuthenticated_whenGetAll_thenOk() throws Exception {
    JwtRequestPostProcessor requestPostProcessor =
        jwt()
//            .authorities(Arrays.asList(
//            new SimpleGrantedAuthority("SCOPE_read"),
//                new SimpleGrantedAuthority("READ"),
//                new SimpleGrantedAuthority("ROLE_ADMIN"))
//            )
            .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "user01")
            );

    mockMvc
        .perform(
            get("/products")
                .with(requestPostProcessor)
        )
        .andDo(print())
        .andExpect(status().isOk());

  }

  @Test
    // Role, Authority check 안됨
  void givenUserIsAuthenticated_whenDelete_thenOk() throws Exception {
    JwtRequestPostProcessor requestPostProcessor =
        jwt()
            .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "user01"));

    mockMvc
        .perform(
            delete("/products/id01")
                .with(requestPostProcessor)
        )
        .andDo(print())
        .andExpect(status().isOk());

  }

  @Test
  void userInfo() throws Exception {
    JwtRequestPostProcessor requestPostProcessor =
        jwt()
//            .authorities(Arrays.asList(
//                new SimpleGrantedAuthority("SCOPE_read"),
//                new SimpleGrantedAuthority("READ"),
//                new SimpleGrantedAuthority("ROLE_ADMIN"))
//            )
            .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "user01"));

    mockMvc
        .perform(
            get("/user-info")
                .with(requestPostProcessor)
        )
        .andDo(print())
        .andExpect(status().isOk());

  }


}
