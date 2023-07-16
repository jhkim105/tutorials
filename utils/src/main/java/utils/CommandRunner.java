package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class CommandRunner {
  public static String run(String... command) {
    String result ;
    Process process = null;
    try {
      if (log.isDebugEnabled()) {
        List<String> commandList = new ArrayList<>();
        Collections.addAll(commandList, command);
        log.debug("command:{}", commandList);
      }

      ProcessBuilder processBuilder = new ProcessBuilder(command);
      processBuilder.redirectErrorStream(true);
      process = processBuilder.start();
      BufferedReader buf = new BufferedReader(new InputStreamReader(process.getInputStream()));
      result = IOUtils.toString(buf);
    } catch (Exception ex) {
      log.debug("{}", ex.toString());
      throw new RuntimeException(ex);
    } finally {
      if (process != null)
        process.destroy();
    }

    log.debug("result:{}", result);

    return StringUtils.removeEnd(result, System.lineSeparator());
  }

}
