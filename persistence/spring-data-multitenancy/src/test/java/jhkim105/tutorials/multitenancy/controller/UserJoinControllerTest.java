package jhkim105.tutorials.multitenancy.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import jhkim105.tutorials.multitenancy.controller.UserJoinController.UserJoinRequest;
import jhkim105.tutorials.multitenancy.master.repository.TenantRepository;
import jhkim105.tutorials.multitenancy.master.service.TenantService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Disabled
class UserJoinControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  TenantRepository tenantRepository;

  @Autowired
  TenantService tenantService;

  final String tenantName = "tenant_1";
  final String username = "user11";
  String tenantId;
  @Test
  @Order(1)
  void join() throws Exception {
    UserJoinRequest userJoinRequest = UserJoinRequest.builder()
        .tenantName(tenantName)
        .username(username)
        .build();
    ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/user-join")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userJoinRequest)))
        .andDo(MockMvcResultHandlers.print());

    result.andExpect(status().isOk());

  }

  @Test
  @Order(2)
  void getAll() throws Exception {
    String tenantId = tenantRepository.findByName(tenantName).getId();
    ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .header("X-Tenant-ID", tenantId))
        .andDo(MockMvcResultHandlers.print());

    result.andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)));
  }

  @Test
  @Order(8)
  void deleteTenant() throws Exception {
    String tenantId = tenantRepository.findByName(tenantName).getId();
    ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete("/tenants/" + tenantId))
        .andDo(MockMvcResultHandlers.print());

    result.andExpect(status().isOk());

    assertThat(tenantRepository.findById(tenantId)).isNotPresent();
  }



  @Test
  @Order(9)
  void dropOrphanDatabases() {
    tenantService.dropOrphanDatabases();
  }

}