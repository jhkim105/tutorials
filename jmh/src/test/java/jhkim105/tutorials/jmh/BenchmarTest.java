package jhkim105.tutorials.jmh;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

public class BenchmarTest {

  @Test
  public void rsCrypto() throws Exception {
    Options opt = new OptionsBuilder()
        .include(RsCryptoBenchmark.class.getName() + ".*")
        .measurementIterations(2)
        .shouldFailOnError(true)
        .shouldDoGC(true)
        .build();
    new Runner(opt).run();
  }

  @Test
  public void rsCryptoComponent() throws Exception {
    Options opt = new OptionsBuilder()
        .include(RsCryptoComponentBenchmark.class.getName() + ".*")
        .measurementTime(TimeValue.seconds(0))
        .measurementIterations(1)
        .shouldFailOnError(true)
        .shouldDoGC(true)
        .build();
    new Runner(opt).run();
  }

  @Test
  public void roundRobin() throws Exception {
    Options opt = new OptionsBuilder()
        .include(RoundRobinBenchmark.class.getName() + ".*")
//        .measurementTime(TimeValue.seconds(0))
        .measurementIterations(1)
        .shouldFailOnError(true)
        .shouldDoGC(true)
        .build();
    new Runner(opt).run();
  }
}
