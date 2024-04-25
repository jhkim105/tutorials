package jhkim105.tutorials;

import io.tus.java.client.ProtocolException;
import io.tus.java.client.TusClient;
import io.tus.java.client.TusExecutor;
import io.tus.java.client.TusURLMemoryStore;
import io.tus.java.client.TusUpload;
import io.tus.java.client.TusUploader;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.junit.jupiter.api.Test;

class TusClientTest {



  @Test
  void upload() throws Exception {
    File file = new File("src/test/resources/test.pdf");

    TusClient client = new TusClient();
    client.setUploadCreationURL(new URL("http://localhost:8080/tus"));
    client.enableResuming(new TusURLMemoryStore());

    final TusUpload upload = new TusUpload(file);

    System.out.println("Starting upload...");

    TusExecutor executor = new TusExecutor() {
      @Override
      protected void makeAttempt() throws ProtocolException, IOException {

        TusUploader uploader = client.resumeOrCreateUpload(upload);

        // TusUploader uploader = client.beginOrResumeUploadFromURL(upload, new URL("https://tus.server.net/files/my_file"));

        // Upload the file in chunks of 1KB sizes.
        uploader.setChunkSize(1024);

        // file has been fully uploaded the method will return -1
        do {
          long totalBytes = upload.getSize();
          long bytesUploaded = uploader.getOffset();
          double progress = (double) bytesUploaded / totalBytes * 100;

          System.out.printf("Upload at %06.2f%%.\n", progress);
        } while (uploader.uploadChunk() > -1);

        // Allow the HTTP connection to be closed and cleaned up
        uploader.finish();

        System.out.println("Upload finished.");
        System.out.format("Upload available at: %s", uploader.getUploadURL().toString());
      }
    };
    executor.makeAttempts();
  }
}
