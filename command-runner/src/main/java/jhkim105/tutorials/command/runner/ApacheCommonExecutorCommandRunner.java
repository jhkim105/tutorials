package jhkim105.tutorials.command.runner;

import java.io.ByteArrayOutputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteStreamHandler;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;

@Slf4j
public class ApacheCommonExecutorCommandRunner {

  public static String run(String command, int timeout) {
    String result;
    CommandLine cmdLine = CommandLine.parse(command);
    DefaultExecutor executor = new DefaultExecutor();
    ExecuteWatchdog watchdog = new ExecuteWatchdog(timeout * 1000L);
    executor.setWatchdog(watchdog);

    DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();

    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
      ExecuteStreamHandler streamHandler = new PumpStreamHandler(outputStream);

      executor.execute(cmdLine, resultHandler);
      executor.setStreamHandler(streamHandler);
      resultHandler.waitFor();
      int exitCode = resultHandler.getExitValue();
      if (exitCode != 0 ) {
        throw new RuntimeException("Process exited with an error. exitCode:" + exitCode);
      }
      result = outputStream.toString();
      return result;
    } catch (Exception e) {
      throw new RuntimeException(String.format("%s, command:%s", e.getMessage(), command));
    }
  }

  public static void runOnly(String command, int timeout) {
    CommandLine cmdLine = CommandLine.parse(command);
    DefaultExecutor executor = new DefaultExecutor();
    ExecuteWatchdog watchdog = new ExecuteWatchdog(timeout * 1000L);
    executor.setWatchdog(watchdog);
    try  {
      executor.execute(cmdLine);
    } catch (Exception e) {
      throw new RuntimeException(String.format("%s,  command:%s", e.getMessage(), command));
    }
  }
}
