package jhkim105.tutorials.testing.contextcaching.mock;

import static org.mockito.Mockito.mock;

import jhkim105.tutorials.testing.contextcaching.GreetingService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class MockConfig {

  @Primary
  @Bean
  GreetingService createGreetingServiceMock() {
    return mock(
        GreetingService.class,
        MockReset.withSettings(MockReset.AFTER)
    );
  }
}
