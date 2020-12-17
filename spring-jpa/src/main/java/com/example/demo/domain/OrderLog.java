package com.example.demo.domain;

import com.example.demo.domain.base.BaseEntity;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "dl_order_log")
@Getter
@ToString
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class OrderLog extends BaseEntity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String orderId;

  private String userId;

  private String userName;

  @Type(type = "com.example.demo.domain.base.CommaDelimitedStringsJavaTypeDescriptor")
  @Column(name = "address")
  private List<String> addressList;

  @Type(type = "com.example.demo.domain.base.CommaDelimitedStringsJavaTypeDescriptor")
  @Column(name = "product_name")
  private List<String> productNameList;

  @Builder
  public OrderLog(String orderId, String userId, String userName, List<String> addressList,
      List<String> productNameList) {
    this.orderId = orderId;
    this.userId = userId;
    this.userName = userName;
    this.addressList = addressList;
    this.productNameList = productNameList;
  }
}
