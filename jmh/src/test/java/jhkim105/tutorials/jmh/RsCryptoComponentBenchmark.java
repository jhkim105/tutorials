package jhkim105.tutorials.jmh;

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class RsCryptoComponentBenchmark {
  @State(Scope.Thread)
  public static class BenchmarkState {
    ApplicationContext context;
    RsCryptoComponent rsCryptoComponent;

    @Setup(Level.Trial)
    public synchronized void init() {
      try {
        if (context == null) {
          context = new AnnotationConfigApplicationContext(JmhApplication.class);
        }
        rsCryptoComponent = context.getBean(RsCryptoComponent.class);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @OutputTimeUnit(TimeUnit.SECONDS)
  @Warmup(iterations = 0)
  @Threads(1)
  @Fork(1)
  public void benchmark1(BenchmarkState state, Blackhole bh) {
    String enc = state.rsCryptoComponent.encrypt("abc");
    log.debug("encrypt:{}", enc);
  }
}
