package jhkim105.tutorials.multitenancy.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import jhkim105.tutorials.multitenancy.domain.User;
import jhkim105.tutorials.multitenancy.master.repository.TenantRepository;
import jhkim105.tutorials.multitenancy.tenant.TenantInterceptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  TenantRepository tenantRepository;

  private String tenantId;

  @BeforeEach
  void setUp() {
    tenantId = tenantRepository.findByName("user1").getId();
  }

  @Test
  void getAll() throws Exception {
    ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .header(TenantInterceptor.HEADER_TENANT_ID, tenantId))
        .andDo(MockMvcResultHandlers.print());

    result.andExpect(status().isOk());
  }

  @Test
  @Transactional(transactionManager = "tenantTransactionManager")
  void create() throws Exception {
    User user = User.builder().username("username100").build();

    ResultActions result = mockMvc.perform(
        MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .header(TenantInterceptor.HEADER_TENANT_ID, tenantId)
            .content(objectMapper.writeValueAsString(user)))
        .andDo(MockMvcResultHandlers.print());

    result.andExpect(status().isOk());
  }


}