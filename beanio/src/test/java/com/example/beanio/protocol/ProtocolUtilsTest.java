package com.example.beanio.protocol;

import com.example.beanio.protocol.ProtocolUtils.Protocol;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class ProtocolUtilsTest {

  @Autowired
  ProtocolUtils protocolUtils;

  @Test
  void serializeAndDeserialize() {
    Protocol message = Protocol.builder()
        .mobile("01011112222")
        .message("this is message")
        .build();
    String protocol = protocolUtils.serialize(message);
    log.info(protocol);

    message= protocolUtils.deserialize(protocol);
    log.info("{}", message);
  }

}