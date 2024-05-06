package jhkim105.tutorials.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "t_group")
@Getter
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Group {
  @Id
  @UuidGenerator
  @Column(length = 50)
  private String id;

  @Column(nullable = false)
  private String name;

  @ManyToMany
  private Set<User> users = new HashSet<>();

  // LAZY 인 경우 n+1 select 가 발생함
  @OneToOne(mappedBy = "group", optional = false, fetch = FetchType.LAZY)
//  @OneToOne(mappedBy = "group", optional = false)
  private Setting setting;

  @Builder
  public Group(String name) {
    this.name = name;
  }
}
