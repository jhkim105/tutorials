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
class TusTest {

  @Autowired
  private TusFileUploadService tusFileUploadService;


  @Test
  @Disabled
  void uploadInfo() throws Exception{
    var tusId = "1ec4b010-02b3-4469-bf08-bff852174150";
    UploadInfo uploadInfo = tusFileUploadService.getUploadInfo(tusId);
    log.info("uploadInfo id: {}, filename: {}, length: {}, mimeTYpe: {}",
        uploadInfo.getId(), uploadInfo.getFileName(), uploadInfo.getLength(), uploadInfo.getFileMimeType());

    UploadInfo uploadInfo2 = tusFileUploadService.getUploadInfo("/tus/" + tusId);
    log.info("uploadInfo id: {}, filename: {}, length: {}, mimeTYpe: {}",
        uploadInfo2.getId(), uploadInfo2.getFileName(), uploadInfo2.getLength(), uploadInfo2.getFileMimeType());
  }


  @Test
  void cleanup() throws Exception{
    tusFileUploadService.cleanup();
  }


}
