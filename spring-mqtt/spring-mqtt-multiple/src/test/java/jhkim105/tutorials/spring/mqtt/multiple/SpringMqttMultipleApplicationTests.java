package jhkim105.tutorials.spring.mqtt.multiple;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class SpringMqttMultipleApplicationTests {

  @Autowired(required = false)
  private MqttUtils mqttUtils;

  @Autowired
  private IntegrationFlowContext integrationFlowContext;

  @Autowired
  protected MockMvc mockMvc;

  @Test
  void publish() {
    mqttUtils.publish("/test2", "aaaa");
  }

  @Test
  void refresh() throws Exception {
    ResultActions result = mockMvc.perform(
        MockMvcRequestBuilders.get("/refresh")
    ).andDo(print());

    result.andExpect(status().isOk());
  }

}
