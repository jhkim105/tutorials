package jhkim105.tutorials.es.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.ToString;

@Embeddable
@ToString
@Getter
public class Address {

  @Column(name = "adress_name")
  private String name;

  @Column(name = "adress_city")
  private String city;

  @Column(name = "adress_street")
  private String street;

}
