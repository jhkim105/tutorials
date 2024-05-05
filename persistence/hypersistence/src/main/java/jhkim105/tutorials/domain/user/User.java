package jhkim105.tutorials.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "dm_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User  {

  @Id
  @UuidGenerator
  @Column(length = 50)
  private String id;

  @Column(nullable = false)
  private String username;

  @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, optional = false)
  private Membership membership;

  @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, optional = false)
  private UserProfile profile;

  public User(String username) {
    this.username = username;
  }



}
