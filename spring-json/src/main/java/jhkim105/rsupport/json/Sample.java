package jhkim105.rsupport.json;


import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
//@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE) // json deserialize issue
@ToString
public class Sample {

  private String id;

  private String name;

  public Sample(String name) {
    this.id = UUID.randomUUID().toString();
    this.name = name;
  }


}