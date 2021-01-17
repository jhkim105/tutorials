package com.example.springintegrationdynamic;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(of = {"id"})
@ToString
public class MessageDto implements Serializable {

  private static final long serialVersionUID = 8014775223429114246L;
  private String id;
  private LocalDateTime createdAt;


  public MessageDto(String id) {
    this.id = id;
    this.createdAt = LocalDateTime.now();
  }

}
