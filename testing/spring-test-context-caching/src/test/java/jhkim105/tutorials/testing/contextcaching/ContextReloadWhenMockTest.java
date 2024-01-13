package jhkim105.tutorials.testing.contextcaching;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest
class ContextReloadWhenMockTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private GreetingService greetingService;


  @Test
  void greet() throws Exception{
    when(greetingService.greet()).thenReturn("Hello, Mock");
    this.mockMvc.perform(get("/greeting"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Hello, Mock")));;
  }


}