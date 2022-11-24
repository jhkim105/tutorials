package jhkim105.tutorials.spring.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageServiceImpl implements StorageService{

  private final AppProperties appProperties;

  @Override
  public Resource loadAsResource(String filename) {
    String path =  appProperties.getStoragePath() + File.separator + filename;
    return new FileSystemResource(path);
  }

  @Override
  public void store(MultipartFile multipartFile) {
    store(multipartFile, null);
  }

  @Override
  public void store(MultipartFile multipartFile, String key) {
    if (!StringUtils.hasText(key)) {
      key = multipartFile.getOriginalFilename();
    }
    String destFilePath = appProperties.getStoragePath() + File.separator + key;
    File dest = new File(destFilePath);
    if (!dest.getParentFile().exists()) {
      boolean created = dest.getParentFile().mkdirs();
      log.debug("dir created: {}", created);
    }

    try {
      multipartFile.transferTo(dest);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }



  @Override
  public Stream<Path> loadAll() {
    try {
      return Files.list(Paths.get(appProperties.getStoragePath()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
