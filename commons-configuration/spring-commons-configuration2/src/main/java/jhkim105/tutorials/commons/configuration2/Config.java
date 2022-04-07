package jhkim105.tutorials.commons.configuration2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "zt_config")
@Getter @Setter
public class Config {

  @Id
  @Column(length = 50)
  private String id;

  @Column(length = 512)
  private String value;

}