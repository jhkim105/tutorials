package com.example.demo.base;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass()
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractTimestampEntity<K extends Serializable> extends AbstractCreateTimestampEntity<K> {

  private static final long serialVersionUID = -1776653546324533189L;

  @Column(name = "updated_date")
  @LastModifiedDate
  protected Long updatedDate;

}
