package jhkim105.tutorials.domain.user;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserProjection {

  private String id;

  private String username;

  private String etc;

  @QueryProjection
  public UserProjection(String id, String username) {
    this.id = id;
    this.username = username;
  }

  @QueryProjection
  public UserProjection(String id, String username, String etc) {
    this.id = id;
    this.username = username;
    this.etc = etc;
  }
}
