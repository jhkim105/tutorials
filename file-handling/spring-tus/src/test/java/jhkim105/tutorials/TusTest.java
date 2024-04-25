package jhkim105.tutorials;


import lombok.extern.slf4j.Slf4j;
import me.desair.tus.server.TusFileUploadService;
import me.desair.tus.server.upload.UploadInfo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
@Disabled
class TusTest {

  @Autowired
  private TusFileUploadService tusFileUploadService;


  @Test
  void uploadInfo() throws Exception{
    UploadInfo uploadInfo = tusFileUploadService.getUploadInfo("59f7dc20-8c4b-4e94-a868-2228eb6b7c37");
    log.info("uploadInfo id: {}, filename: {}, length: {}, mimeTYpe: {}",
        uploadInfo.getId(), uploadInfo.getFileName(), uploadInfo.getLength(), uploadInfo.getFileMimeType());

    UploadInfo uploadInfo2 = tusFileUploadService.getUploadInfo("/tus/59f7dc20-8c4b-4e94-a868-2228eb6b7c37");
    log.info("uploadInfo id: {}, filename: {}, length: {}, mimeTYpe: {}",
        uploadInfo2.getId(), uploadInfo2.getFileName(), uploadInfo2.getLength(), uploadInfo2.getFileMimeType());
  }


  @Test
  void cleanup() throws Exception{
    tusFileUploadService.cleanup();
  }


}
