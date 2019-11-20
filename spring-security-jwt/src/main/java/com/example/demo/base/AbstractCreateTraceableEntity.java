package com.example.demo.base;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass()
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractCreateTraceableEntity<K extends Serializable> extends AbstractCreateTimestampEntity<K> {

  private static final long serialVersionUID = -6279828398703077543L;

  @Column(name = "created_by", updatable = false, length = ColumnLength.UUID)
  @CreatedBy
  protected String createdBy;

}
