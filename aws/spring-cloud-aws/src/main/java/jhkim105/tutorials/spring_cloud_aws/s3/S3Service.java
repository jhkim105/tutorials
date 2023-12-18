package jhkim105.tutorials.spring_cloud_aws.s3;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class S3Service {

    private final ResourceLoader resourceLoader;

    public File download(String s3Url, String destDir) {
        Resource resource = resourceLoader.getResource(s3Url);

        File destinationDirectory = new File(destDir);
        if (!destinationDirectory.exists()) {
            destinationDirectory.mkdirs();
        }

        File downloadedFile = new File(destinationDirectory, resource.getFilename());

        try (InputStream inputStream = resource.getInputStream()) {
            Files.copy(inputStream, downloadedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }

      return downloadedFile;
    }
}