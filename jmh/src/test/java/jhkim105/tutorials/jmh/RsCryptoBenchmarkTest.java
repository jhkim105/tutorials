package jhkim105.tutorials.jmh;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class RsCryptoBenchmarkTest {

  @Test
  public void test() throws Exception {
    Options opt = new OptionsBuilder()
        .include(RsCryptoBenchmark.class.getName() + ".*")
        .measurementIterations(2)
        .shouldFailOnError(true)
        .shouldDoGC(true)
        .build();
    new Runner(opt).run();
  }
}