package jhkim105.tutorials.spring.data.envers;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserTests {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void getOne() throws Exception {
    String id = "id01";
    mockMvc.perform(get("/users/{id}", id))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("_links.self").exists())
    ;
  }

  @Test
  void getList() throws Exception {
    mockMvc.perform(get("/users")
            .param("page", "0")
            .param("size", "5")
            .param("sort", "name,DESC")
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$._embedded.users[0]._links.self").exists())
        .andExpect(jsonPath("page").exists())
    ;
  }


}
