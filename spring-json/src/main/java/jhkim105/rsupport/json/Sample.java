package jhkim105.rsupport.json;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE) // json deserialize issue
public class Sample {

  private String name;

  public Sample(String name) {
    this.name = name;
  }


}