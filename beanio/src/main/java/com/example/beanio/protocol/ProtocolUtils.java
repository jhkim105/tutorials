package com.example.beanio.protocol;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.beanio.BeanWriter;
import org.beanio.StreamFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProtocolUtils {

  @Autowired
  StreamFactory factory;

  public String serialize(Protocol protocol) {
    String steamName = "message";
    StringWriter stringWriter = new StringWriter();
    BeanWriter beanWriter = factory.createWriter(steamName, stringWriter);

    Map<String, Object> headerMap = new HashMap<>();
    headerMap.put("id", protocol.id);
    beanWriter.write("header", headerMap);
    Map<String, Object> dataMap = new HashMap<>();
    dataMap.put("mobile", protocol.mobile);
    dataMap.put("message", protocol.message);
    beanWriter.write("data", dataMap);

    String ret = stringWriter.toString();

    beanWriter.flush();
    beanWriter.close();

    return ret;
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
