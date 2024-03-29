package jhkim105.tutorials.reactor;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
class ReactorTests {


  @Test
  void flux() {
    List<Integer> elements = new ArrayList<>();
    Flux<Integer> just = Flux.just(1, 2, 3, 4);
    just.log()
        .map(i -> {
          log.debug("{}, {}", i, Thread.currentThread());
          return i * 2;
        })
        .subscribe(elements::add);

    assertThat(elements).containsExactly(2, 4, 6, 8);
  }

  @Test
  void flow() {
    List<Integer> elements = new ArrayList<>();
    Flux.just(1, 2, 3, 4)
        .log()
        .subscribe(new Subscriber<>() {
          @Override
          public void onSubscribe(Subscription s) {
            s.request(Long.MAX_VALUE);
          }

          @Override
          public void onNext(Integer integer) {
            elements.add(integer);
          }

          @Override
          public void onError(Throwable t) {}

          @Override
          public void onComplete() {}
        });
    log.debug("{}", elements);
  }


  @Test
  void backPressure() {
    List<Integer> elements = new ArrayList<>();

    Flux.just(1, 2, 3, 4)
        .log()
        .subscribe(new Subscriber<Integer>() {
          private Subscription s;
          int onNextAmount;

          @Override
          public void onSubscribe(final Subscription s) {
            this.s = s;
            s.request(2);
          }

          @Override
          public void onNext(final Integer integer) {
            elements.add(integer);
            onNextAmount++;
            if (onNextAmount % 2 == 0) {
              s.request(2);
            }
          }

          @Override
          public void onError(final Throwable t) {
          }

          @Override
          public void onComplete() {
          }
        });

    assertThat(elements).containsExactly(1, 2, 3, 4);
  }



  /**
   * Operating on a Stream - Combining Two Streams
   */
  @Test
  void zip() {
    List<String> elements = new ArrayList<>();

    Flux.just(1, 2, 3, 4)
        .log()
        .map(i -> i * 2)
        .zipWith(Flux.range(0, Integer.MAX_VALUE).log(), (one, two) -> String.format("First Flux: %d, Second Flux: %d", one, two))
        .subscribe(elements::add);

    assertThat(elements).containsExactly(
        "First Flux: 2, Second Flux: 0",
        "First Flux: 4, Second Flux: 1",
        "First Flux: 6, Second Flux: 2",
        "First Flux: 8, Second Flux: 3");
  }


  @Test
  void connectableFlux() {
    List<Integer> elements = new ArrayList<>();
    final ConnectableFlux<Integer> publish = Flux.just(1, 2, 3, 4).publish();
    publish.subscribe(elements::add);
    assertThat(elements).isEmpty();
    publish.connect();
    assertThat(elements).containsExactly(1, 2, 3, 4);
  }


  @Test
  void concurrency() throws InterruptedException {
    List<String> threadNames = new ArrayList<>();

    Flux.just(1, 2, 3, 4)
        .log()
        .map(i -> Thread.currentThread().getName())
        .subscribeOn(Schedulers.parallel())
        .subscribe(threadNames::add);

    Thread.sleep(1000);

    assertThat(threadNames).isNotEmpty();
    assertThat(threadNames).hasSize(4);
  }
}
