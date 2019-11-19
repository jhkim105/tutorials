package jhkim105.tutorials.jmh;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

class RsCryptoComponentBenchmarkTest {

  @Test
  public void test() throws Exception {
    Options opt = new OptionsBuilder()
        .include(RsCryptoComponentBenchmark.class.getName() + ".*")
        .measurementTime(TimeValue.seconds(0))
        .measurementIterations(1)
        .shouldFailOnError(true)
        .shouldDoGC(true)
        .build();
    new Runner(opt).run();
  }
}