package jhkim105.tutorials.spring.thymeleaf;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
public enum Role {
  ADMIN, USER;
  @Setter
  private String label;

}
