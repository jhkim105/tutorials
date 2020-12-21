package com.example.beanio.protocol;

import com.example.beanio.ProtocolProperties;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.beanio.BeanReader;
import org.beanio.BeanWriter;
import org.beanio.StreamFactory;
import org.beanio.Unmarshaller;
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
      beanWriter.write("message1", protocol);
      String ret = stringWriter.toString();
      log.debug("length:{}, protocol:{}", ret.length(), ret);
//      checkLength(ret);
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


  public Map<String, Object> deserialize(String protocolString, String recordName) {
    String steamName = "message";
    Unmarshaller unmarshaller = streamFactory.createUnmarshaller(steamName);
    log.info("unmarshaller.getRecordName():{}", unmarshaller.getRecordName());
    Map<String, Object> map = (Map<String, Object>)unmarshaller.unmarshal(recordName);
    return map;
  }

  public Protocol deserialize(String protocolString) {
    String steamName = "message";
    StringReader reader = new StringReader(protocolString);
    BeanReader beanReader = streamFactory.createReader(steamName, reader);
    Protocol protocol = (Protocol)beanReader.read();
    beanReader.close();
    return protocol;
  }



  @ToString
  @Getter
  @NoArgsConstructor
  public static class Protocol {
    private Map<String, Object> header = new HashMap<>();
    private Map<String, Object> data = new HashMap<>();

    private String end;

    @Builder
    public Protocol(String mobile, String message) {
      header.put("id", UUID.randomUUID().toString());
      data.put("mobile", mobile);
      data.put("message", message);
    }

  }


}
