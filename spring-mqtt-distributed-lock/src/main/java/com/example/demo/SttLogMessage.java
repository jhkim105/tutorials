package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import org.apache.commons.lang3.StringUtils;

@lombok.Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SttLogMessage implements Serializable {

  private static final long serialVersionUID = -4900854111905704852L;

  @JsonProperty("speechToText")
  private SttLogMessage.Data data;

  public boolean isNotValid() {
    return data == null
        || StringUtils.isBlank(data.conferenceId)
        || StringUtils.isBlank(data.endpointId)
        || StringUtils.isBlank(data.sentence)
        || data.timestamp == null
        || data.seq == null
        || data.confidence < 1.0
        ;
  }

  @lombok.Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Data {

    private static final long serialVersionUID = -5795681830862158710L;

    @JsonProperty("conferenceID")
    private String conferenceId;

    private Integer seq;

    @JsonProperty("endpointID")
    private String endpointId;

    private String sentence;

    private Double confidence;

    private Long timestamp;


  }

}
