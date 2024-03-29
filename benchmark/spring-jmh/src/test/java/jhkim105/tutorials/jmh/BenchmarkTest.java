package jhkim105.tutorials.jmh;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

//@Disabled
class BenchmarTest {

  @Test
  void rsCrypto() throws Exception {
    Options opt = new OptionsBuilder()
        .include(RsCryptoBenchmark.class.getName() + ".*")
        .measurementIterations(2)
        .shouldFailOnError(true)
        .shouldDoGC(true)
        .build();
    new Runner(opt).run();
  }

  @Test
  void rsCryptoComponent() throws Exception {
    Options opt = new OptionsBuilder()
        .include(RsCryptoComponentBenchmark.class.getName() + ".*")
        .measurementIterations(2)
        .shouldFailOnError(true)
        .shouldDoGC(true)
        .build();
    new Runner(opt).run();
  }

  @Test
  void roundRobin() throws Exception {
    Options opt = new OptionsBuilder()
        .include(RoundRobinBenchmark.class.getName() + ".*")
        .measurementIterations(1)
        .shouldFailOnError(true)
        .shouldDoGC(true)
        .build();
    new Runner(opt).run();
  }

  @Test
  @Disabled
  void fileCrypto() throws Exception {
    Options opt = new OptionsBuilder()
        .include(FileCryptoBenchmark.class.getName() + ".*")
        .measurementIterations(1)
        .shouldFailOnError(true)
        .shouldDoGC(true)
        .build();
    new Runner(opt).run();
  }
}
