package com.example.springintegrationdynamic.data;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "dm_message_log")
@Getter
@ToString
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageLog {

  @Id
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @GeneratedValue(generator = "uuid")
  @Column(length = 50)
  private String id;

  @Column(nullable = false)
  private String messageId;
  @Column(nullable = false)
  private LocalDateTime messageCreatedDate;

  @CreatedDate // TODO
  @Column(nullable = false)
  private LocalDateTime createdDate;

  @Builder
  public MessageLog(String messageId, LocalDateTime messageCreatedDate) {
    this.messageId = messageId;
    this.messageCreatedDate = messageCreatedDate;
    this.createdDate = LocalDateTime.now();
  }
}
