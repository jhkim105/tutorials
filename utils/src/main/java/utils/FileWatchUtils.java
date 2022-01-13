package utils;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileWatchUtils {

  public static void waitForCreated(String dirPath, String fileName) {
    try (WatchService watchService = FileSystems.getDefault().newWatchService();) {
      Path path = Paths.get(dirPath);
      path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
      WatchKey key;
      while ((key = watchService.take()) != null) {
        for (WatchEvent<?> event : key.pollEvents()) {
          log.info("event:{}, file:{}", event.kind(), event.context());
          if (event.kind().equals(StandardWatchEventKinds.ENTRY_CREATE) && StringUtils.endsWith(event.context().toString(), fileName)) {
            return;
          }
        }
        key.reset();
      }

    } catch(IOException | InterruptedException ex) {
      throw new RuntimeException(ex);
    }
  }

  public static void waitForCreated(String dirPath, String fileName, long timeoutSeconds) {
    try (WatchService watchService = FileSystems.getDefault().newWatchService();) {
      Path path = Paths.get(dirPath);
      path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
      WatchKey key;;
      while ((key = watchService.poll(timeoutSeconds, TimeUnit.SECONDS)) != null) {
        for (WatchEvent<?> event : key.pollEvents()) {
          log.info("event:{}, file:{}", event.kind(), event.context());
          if (event.kind().equals(StandardWatchEventKinds.ENTRY_CREATE) && StringUtils.endsWith(event.context().toString(), fileName)) {
            return;
          }
        }
        key.reset();
      }

    } catch(IOException | InterruptedException ex) {
      throw new RuntimeException(ex);
    }
  }

}
