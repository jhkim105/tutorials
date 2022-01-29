package jhkim105.tutorials.spring.data.envers.domain;

import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.ToString;

@Embeddable
@ToString
@Getter
public class Address {

  private String city;

  private String street;

}
