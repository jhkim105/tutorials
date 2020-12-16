package com.example.beanio.protocol;

import com.example.beanio.ProtocolProperties;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.beanio.BeanWriter;
import org.beanio.StreamFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProtocolUtils {

  private final StreamFactory streamFactory;

  private final ProtocolProperties protocolProperties;

  public String serialize(Protocol protocol) {
    String steamName = "message";
    StringWriter stringWriter = new StringWriter();
    BeanWriter beanWriter = streamFactory.createWriter(steamName, stringWriter);

    try {
      Map<String, Object> headerMap = new HashMap<>();
      headerMap.put("id", protocol.id);
      beanWriter.write("header", headerMap);
      Map<String, Object> dataMap = new HashMap<>();
      dataMap.put(protocolProperties.getPhoneNumberFieldName(), protocol.mobile);
      dataMap.put(protocolProperties.getMessageFieldName(), protocol.message);
      beanWriter.write("data", dataMap);

      String ret = stringWriter.toString();
      log.debug("length:{}, protocol:{}", ret.length(), ret);
      checkLength(ret);
      return ret;
    } finally {
      beanWriter.flush();
      beanWriter.close();
    }

  }

  private void checkLength(String ret) {
    int parseLength = parseLength(ret);
    if(parseLength != ret.length()) {
      throw new RuntimeException(String.format("message length invalid. expected:%s, actual:%s", parseLength, ret.length()));
    }
  }

  private int parseLength(String ret) {
    String lenStr = ret.substring(0, 8);
    return Integer.parseInt(lenStr);
  }

  @ToString
  @Getter
  public static class Protocol {

    private String id;
    private String kind;
    private String mobile;
    private String message;

    @Builder
    public Protocol(String kind, String mobile, String message) {
      this.id = UUID.randomUUID().toString();
      this.kind = kind;
      this.mobile = mobile;
      this.message = message;
    }

  }


}
