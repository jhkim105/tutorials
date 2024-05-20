package jhkim105.tutorials;


import me.desair.tus.server.TusFileUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TusConfig {

  @Value("#{servletContext.contextPath}")
  private String contextPath;

  @Value("${tus.storage-path}")
  protected String tusStoragePath;

  @Bean
  public TusFileUploadService tusFileUploadService() {
    return new TusFileUploadService()
        .withStoragePath(tusStoragePath)
        .withDownloadFeature()
        .withUploadUri(contextPath + "/tus")
        .withThreadLocalCache(true);

  }

}
