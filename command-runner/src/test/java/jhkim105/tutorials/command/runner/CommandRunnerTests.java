package jhkim105.tutorials.command.runner;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class CommandRunnerTests {


  @Test
  void processBuilder() {
    String result = ProcessBuilderCommandRunner.run("sleep", "3");
    log.info("{}", result);

  }

  @Test
  void apacheCommonExecutor() {
    String result = ApacheCommonExecutorCommandRunner.run("sleep 2", 3);
    log.info("{}", result);

  }
}
