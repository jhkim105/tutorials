package jhkim105.tutorials.multitenancy.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jhkim105.tutorials.multitenancy.tenant.TenantInterceptor;
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
@Transactional
class UserControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
//  @Sql(scripts = {"/tenant.sql"}, config = @SqlConfig(errorMode = ErrorMode.CONTINUE_ON_ERROR))
  void getAll() throws Exception {
    ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/users")
        .contentType(MediaType.APPLICATION_JSON)
            .header(TenantInterceptor.HEADER_X_TENANT_ID, "company1"))
        .andDo(MockMvcResultHandlers.print());

    result.andExpect(status().isOk());
  }
}