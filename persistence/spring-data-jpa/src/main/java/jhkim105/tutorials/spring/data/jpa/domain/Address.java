package jhkim105.tutorials.spring.data.jpa.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.ToString;

@Embeddable
@ToString
@Getter
public class Address {

  @Column(name = "address_name")
  private String name;

  @Column(name = "address_city")
  private String city;

  @Column(name = "address_street")
  private String street;

}
