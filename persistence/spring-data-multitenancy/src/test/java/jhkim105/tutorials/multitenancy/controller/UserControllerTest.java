package jhkim105.tutorials.multitenancy.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import jhkim105.tutorials.multitenancy.controller.UserController.UserCreateRequest;
import jhkim105.tutorials.multitenancy.master.domain.Tenant;
import jhkim105.tutorials.multitenancy.master.repository.TenantRepository;
import jhkim105.tutorials.multitenancy.master.service.TenantService;
import jhkim105.tutorials.multitenancy.tenant.context.TenantFilter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@SpringBootTest
@AutoConfigureMockMvc
@Disabled
class UserControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  TenantRepository tenantRepository;

  @Autowired
  TenantService tenantService;

  private Tenant tenant;
  private final String tenantName = "tenant_t01";

  @BeforeEach
  void setUp() {
    tenant = tenantService.createTenant(tenantName);
  }

  @AfterEach()
  void tearDown() {
    tenantService.deleteTenant(tenant);
    tenantService.dropOrphanDatabases();
  }

  @Test
  void getAll() throws Exception {
    ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .header(TenantFilter.X_TENANT_ID_HEADER, tenant.getId()))
        .andDo(MockMvcResultHandlers.print());

    result.andExpect(status().isOk());
  }


  @Test
  void create() throws Exception {
    UserCreateRequest userCreateRequest = UserCreateRequest.builder()
        .tenantId(tenant.getId())
        .username("user_t0101")
        .build();

    ResultActions result = mockMvc.perform(
        MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreateRequest)))
        .andDo(MockMvcResultHandlers.print());

    result.andExpect(status().isOk());
  }


}