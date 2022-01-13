package jhkim105.tutorials.spring.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService{

  private final AppProperties appProperties;

  @Override
  public Resource loadAsResource(String filename) {
    String path =  appProperties.getStoragePath() + File.separator + filename;
    return new FileSystemResource(path);
  }

  @Override
  public void store(MultipartFile multipartFile) {
    String destFilePath = appProperties.getStoragePath() + File.separator + multipartFile.getOriginalFilename();
    File dest = new File(destFilePath);
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
