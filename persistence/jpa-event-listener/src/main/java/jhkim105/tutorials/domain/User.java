package jhkim105.tutorials.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(exclude = "group")
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class User {

  @Id
  @UuidGenerator
  @Column(length = 50)
  private String id;

  @Column(nullable = false)
  private String username;

  @ManyToOne
  private Group group;

  @Column
  private String description;


  @Column(name = "created_dt", updatable = false)
  @CreatedDate
  protected Instant createdDt;

  public User(String username) {
    this.username = username;
  }

  public User(Group group, String username) {
    this.group = group;
    this.username = username;
  }



  @PrePersist
  void prePersist() {
    log.debug(">> User.prePersist");
  }

  @PostPersist
  void postPersist() {
    log.debug(">> User.postPersist");
  }

  @PreUpdate
  void preUpdate() {
    log.debug(">> User.preUpdate");
  }

  @PostUpdate
  void postUpdate() {
    log.debug(">> User.postUpdate");
  }
  
  
}
