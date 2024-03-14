package jhkim105.tutorials.rabbitmq;


import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SampleMessage implements Serializable {

  @Serial
  private static final long serialVersionUID = 1918600819179000566L;

  private String name;


}
