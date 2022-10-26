package com.example.demo.base;


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass()
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractTraceableEntity<K extends Serializable> extends AbstractCreateTraceableEntity<K> {

  private static final long serialVersionUID = 2379933612991151510L;

    @Column(name = "updated_date")
    @LastModifiedDate
    protected Long updatedDate;

    @Column(name = "updated_by", length = ColumnLength.UUID)
    @LastModifiedBy
    protected String updatedBy;

}