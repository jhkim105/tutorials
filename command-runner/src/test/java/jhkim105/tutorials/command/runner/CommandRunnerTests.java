package jhkim105.tutorials.command.runner;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
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


  @Test
  void apacheCommonExecutor_run() {
    Assertions.assertThrows(RuntimeException.class, () -> {
      String result = ApacheCommonExecutorCommandRunner.run("dir", 3);
    });
  }

  @Test
  void apacheCommonExecutor_run_throws_exception() {
    Assertions.assertThrows(RuntimeException.class, () -> {
      ApacheCommonExecutorCommandRunner.run("ping", 3);
    });
  }

  @Test
  void apacheCommonExecutor_runOnly() {
    ApacheCommonExecutorCommandRunner.runOnly("ls", 3);
  }

  @Test
  void apacheCommonExecutor_runOnly_throws_exception() {
    Assertions.assertThrows(RuntimeException.class, () -> {
      ApacheCommonExecutorCommandRunner.runOnly("ping", 3);
    });
  }

  @Test
  void apacheCommonExecutor_runOnly_throws_exception_timeout() {
    Assertions.assertThrows(RuntimeException.class, () -> {
      ApacheCommonExecutorCommandRunner.runOnly("sleep 3", 1);
    });
  }
}
