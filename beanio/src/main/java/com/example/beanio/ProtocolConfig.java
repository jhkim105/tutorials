package com.example.beanio;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.beanio.StreamFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;

@Configuration
@Slf4j
public class ProtocolConfig {

  @Autowired
  private ResourceLoader resourceLoader;

  @Autowired
  private ProtocolProperties protocolProperties;

  @Bean
  public StreamFactory streamFactory() throws IOException {
    StreamFactory streamFactory = StreamFactory.newInstance();
    String mappingFilePath = protocolProperties.getMappingFilePath();
    log.info("mapping-file.path:{}", mappingFilePath);
    if (!StringUtils.hasText(mappingFilePath)) {
      throw new IllegalStateException("mapping-file.path is empty.");
    }
    Resource resource = resourceLoader.getResource(mappingFilePath);
    log.debug("mapping-file:{}", resource.getFile().getAbsolutePath());
    streamFactory.load(resource.getFile());
    return streamFactory;
  }
}
