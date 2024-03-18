package jhkim105.tutorials.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
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
@Table(name = "t_group")
@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(exclude = "users")
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class Group {

  @Id
  @UuidGenerator
  @Column(length = 50)
  private String id;

  @Column(nullable = false)
  private String name;

  @OneToMany(mappedBy = "group")
  private Set<User> users = new HashSet<>();

  @Column
  private String description;

  @Column(name = "created_dt", updatable = false)
  @CreatedDate
  protected Instant createdDt;
  public Group(String name) {
    this.name = name;
  }

  @PrePersist
  void prePersist() {
    log.debug(">> Group.prePersist");
  }

  @PostPersist
  void postPersist() {
    log.debug(">> Group.postPersist");
  }

  @PreUpdate
  void preUpdate() {
    log.debug(">> Group.prePersist");
  }

  @PostUpdate
  void postUpdate() {
    log.debug(">> Group.postPersist");
  }

}

