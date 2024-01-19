package jhkim105.tutorials.io;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

class FileWatchTest {


  @Test
  void test() throws Exception {
    System.out.println("result: " + readValue(Paths.get("tmp/"),  Paths.get("tmp/info.txt"), 60));;
  }

  private String readValue(Path dirPath, Path target,  int timeoutSeconds) {
    try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
      dirPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);

      WatchKey key;
      while ((key = watchService.poll(timeoutSeconds, TimeUnit.SECONDS)) != null) {
        for (WatchEvent<?> event : key.pollEvents()) {
          String value = handleWatchEvent(event, target);
          if (value == null || value.isBlank()) {
            break;
          }
          return value;
        }
        key.reset();
      }

    } catch (IOException | InterruptedException ex) {
      throw new IllegalStateException(ex);
    }

    throw new IllegalStateException("Fail to read file");
  }

  private String handleWatchEvent(WatchEvent<?> event, Path target) {
    if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE || event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
      Path contextPath = (Path) event.context();
      System.out.println("file: " + contextPath.toFile().getName());
      if (contextPath.toFile().getName().equals(target.toFile().getName())) {
        return readString(target);
      }
    }
    return null;
  }

  private String readString(Path path) {
    try {
      String value = Files.readString(path);
      System.out.println("readString: " + value);
      if (value.isBlank()) {
        value =  Files.readString(path);
      }
      return value;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
