package jhkim105.tutorials;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.servlet.RequestDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@Slf4j
public class ErrorDocumentationTest extends BaseConrollerTest {


  @Autowired
  private MockMvc mockMvc;

  @Test
  @Disabled
  public void errorExample() throws Exception {
    this.mockMvc
        .perform(MockMvcRequestBuilders.get("/error")
            .requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 400)
            .requestAttr(RequestDispatcher.ERROR_REQUEST_URI, "/users")
            .requestAttr(RequestDispatcher.ERROR_MESSAGE, "The user 'http://localhost:8080/users/123' does not exist")
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("error").value("Bad Request"))
        .andExpect(jsonPath("timestamp").isNotEmpty())
        .andExpect(jsonPath("status").value(400))
        .andExpect(jsonPath("path").isNotEmpty())
        .andDo(print())
        .andDo(document("error-example",
            responseFields(
                fieldWithPath("error").description("The HTTP error that occurred, e.g. `Bad Request`"),
                fieldWithPath("path").description("The path to which the request was made"),
                fieldWithPath("status").description("The HTTP status code, e.g. `400`"),
                fieldWithPath("timestamp").description("The time, in milliseconds, at which the error occurred"))));
  }

  @Test
  public void errorTest() throws Exception {
    this.mockMvc
        .perform(MockMvcRequestBuilders.get("/error/test"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("code").value("9999"))
        .andExpect(jsonPath("timestamp").isNotEmpty())

        .andDo(print())
        .andDo(document("error-example",
            responseFields(
                fieldWithPath("code").description("Error code"),
                fieldWithPath("message").description("Error message"),
                fieldWithPath("timestamp").description("The time, in milliseconds, at which the error occurred"))));
  }
}
