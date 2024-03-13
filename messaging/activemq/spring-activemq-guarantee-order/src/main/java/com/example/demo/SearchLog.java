package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EqualsAndHashCode(callSuper = false, of = "id")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class SearchLog {

  @Id
  @GeneratedValue
  private Long id;

  private String keyword;

  @CreatedDate
  private Long createdDate;

  @Builder
  public SearchLog(String keyword) {
    this.keyword = keyword;
  }
}
