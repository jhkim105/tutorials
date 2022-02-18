package jhkim105.tutorials.spring.cloud.function.aws.functions;

import java.util.function.Function;

public class Greeter implements Function<String, String> {

  @Override
  public String apply(String s) {
    System.out.println("param:" + s);
    return "Hello " + s;
  }
}
