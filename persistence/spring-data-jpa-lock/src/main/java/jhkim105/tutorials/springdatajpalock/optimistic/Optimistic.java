package jhkim105.tutorials.springdatajpalock.optimistic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Optimistic {

  @Id
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @GeneratedValue(generator = "uuid")
  @Column(length = 50)
  private String id;

  @Column(length = 100, unique = true)
  private String name;

  @ColumnDefault("0")
  @Column(nullable = false)
  private long count;

  @Version
  private Integer version;

  public void incr() {
    count++;
  }



}
