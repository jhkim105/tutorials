package jhkim105.tutorials.spring.files;

import java.nio.file.Path;
import java.util.stream.Stream;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


public interface StorageService {

  Resource loadAsResource(String filename);

  void store(MultipartFile multipartFile);
  void store(MultipartFile multipartFile, String key);

  Stream<Path> loadAll();

}
