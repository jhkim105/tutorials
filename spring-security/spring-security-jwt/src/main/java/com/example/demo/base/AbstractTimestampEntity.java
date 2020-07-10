package com.example.demo.base;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

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
