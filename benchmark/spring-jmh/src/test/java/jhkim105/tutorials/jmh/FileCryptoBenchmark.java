package jhkim105.tutorials.jmh;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.crypto.KeyGenerator;
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
import utils.crypto.FileCrypto;

@Slf4j
public class FileCryptoBenchmark {
  @State(Scope.Thread)
  public static class BenchmarkState {
    @Setup(Level.Trial)
    public synchronized void init() {
    }
  }

  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  @Warmup(iterations = 1)
  @Threads(3)
  @Fork(1)
  public void benchmark1(BenchmarkState state, Blackhole bh) throws Exception {
    FileCrypto fileCrypto = new FileCrypto(KeyGenerator.getInstance("AES").generateKey(), "AES/CBC/PKCS5Padding");
    fileCrypto.encrypt("plaintext", "target/temp/" + UUID.randomUUID());
  }
}