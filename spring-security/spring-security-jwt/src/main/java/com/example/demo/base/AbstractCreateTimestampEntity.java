package com.example.demo.base;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass()
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractCreateTimestampEntity<K extends Serializable> extends AbstractEntity<K> {

  private static final long serialVersionUID = -8801727337312553249L;

    @Column(name = "created_date", updatable = false)
    @CreatedDate
    protected Long createdDate;

}
