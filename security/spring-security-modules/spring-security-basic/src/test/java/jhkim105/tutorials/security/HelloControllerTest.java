package jhkim105.tutorials.security;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void helloUnauthorized() throws Exception {
        mockMvc.perform(get("/hello"))
            .andDo(print())
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    void helloAuthorized() throws Exception {
        mockMvc.perform(get("/hello"))
            .andExpect(status().isOk())
            .andExpect(content().string("Hello, World!"));
    }

    @Test
    void helloAuthorizedWithBasicAuth() throws Exception {
        String username = "user";
        String password = "password";
        String basicAuthHeader = "Basic " + basicAuthHeader(username, password);

        mockMvc.perform(get("/hello")
                .header(AUTHORIZATION, basicAuthHeader))
            .andExpect(status().isOk())
            .andExpect(content().string("Hello, World!"));
    }

    private String basicAuthHeader(String username, String password) {
        return String.format("Basic %s",
            Base64.getEncoder().encodeToString(String.format("%s:%s", username, password).getBytes()));
    }
}
