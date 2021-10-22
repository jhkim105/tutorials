package com.example.multitenant.tenant;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.multitenant.master.TenantInterceptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  @Sql(scripts = {"/tenant.sql"})
  void getAll() throws Exception {
    ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/users")
        .contentType(MediaType.APPLICATION_JSON)
            .header(TenantInterceptor.HEADER_X_TENANT_ID, "id01"))
        .andDo(MockMvcResultHandlers.print());

    result.andExpect(status().isOk());
  }
}