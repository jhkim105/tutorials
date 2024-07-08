package jhkim105.tutorials.spring.security.form_login;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MaximumSessionsTests {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void loginOnSecondLoginThenFirstSessionTerminated() throws Exception {
    MvcResult mvcResult = this.mockMvc.perform(formLogin().user("user01").password("111111"))
        .andExpect(authenticated())
        .andReturn();

    MockHttpSession firstLoginSession = (MockHttpSession) mvcResult.getRequest().getSession();

    this.mockMvc.perform(get("/hello").session(firstLoginSession))
          .andExpect(authenticated());

    this.mockMvc.perform(formLogin().user("user01").password("111111"))
        .andExpect(authenticated());

    // first session is terminated by second login
    this.mockMvc.perform(get("/hello").session(firstLoginSession))
        .andExpect(unauthenticated());
  }

}