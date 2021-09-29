package com.jhkim105.completablefuture;

import java.time.LocalTime;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CompletableFutureTest {


  @Test
  void calculate() throws ExecutionException, InterruptedException {
    Future<String> future = calculateAsync();
    String result = future.get();
    log(result);
    Assertions.assertEquals("Hello", result);
  }

  private Future<String> calculateAsync() {
    CompletableFuture<String> future = new CompletableFuture<>();
    Executors.newCachedThreadPool().submit(() -> {
      Thread.sleep(500);
      log("Hello");
      future.complete("Hello");
      return null;
    });
    return future;
  }

  private void log(String msg) {
    System.out.println(String.format("[%s][%s] %s", LocalTime.now(), Thread.currentThread().getName(), msg));
  }


  // 이미 결과를 알고 있는 경우
  @Test
  void completedFuture() throws ExecutionException, InterruptedException {
    Future<String> completableFuture =
        CompletableFuture.completedFuture("Hello");

    String result = completableFuture.get();
    Assertions.assertEquals("Hello", result);
  }

  @Test
  void cancel() {
    CompletableFuture<String> future = new CompletableFuture<>();

    Executors.newCachedThreadPool()
        .submit(() -> {
          Thread.sleep(500);
          future.cancel(false);
          return null;
        });

    Assertions.assertThrows(CancellationException.class, future::get);
  }


  @Test
  void supplyAsync() throws ExecutionException, InterruptedException {
    CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "supplyAsync");
    Assertions.assertEquals("Hello", future.get());
  }


  // no return
  @Test
  void runAsync() throws ExecutionException, InterruptedException {
    CompletableFuture<Void> future = CompletableFuture.runAsync(() -> log("runAsync"));
    Assertions.assertNull(future.get());
  }


  @Test
  void supplyAsync_thenApply() throws ExecutionException, InterruptedException {
    CompletableFuture<String> future =
        CompletableFuture.supplyAsync(() -> "Hello").thenApply(s -> s + " World");
    Assertions.assertEquals("Hello World", future.get());
  }



  @Test
  void supplyAsync_thenAccept() {
    CompletableFuture.supplyAsync(() -> "Hello").thenAccept(this::log);
  }


  @Test
  void supplyAsync_thenRun()  {
    CompletableFuture.supplyAsync(() -> "Hello").thenRun(() -> log("thenRun"));
  }


  // monadic design pattern
  // 순차적으로 수행 f(g(x))
  @Test
  void supplyAsync_thenCompose() throws ExecutionException, InterruptedException {
    CompletableFuture<String> completableFuture
        = CompletableFuture.supplyAsync(() -> "Hello")
        .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " World"));

    Assertions.assertEquals("Hello World", completableFuture.get());
  }

  // 동시에 수행 f(a(x), b(x))
  @Test
  void supplyAsync_thenCombine() throws ExecutionException, InterruptedException {
    CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello")
        .thenCombine(CompletableFuture.supplyAsync(() -> " World"), (s1, s2) -> s1 + s2);

    Assertions.assertEquals("Hello World", completableFuture.get());
  }


  // 여러개의 Future 를 Parallel 하게 수행
  @Test
  void allOf() throws ExecutionException, InterruptedException {
    CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Hello");
    CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "Java");
    CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> "Future");

    CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(future1, future2, future3);

    combinedFuture.get();

    Assertions.assertTrue(future1.isDone());
    Assertions.assertTrue(future2.isDone());
    Assertions.assertTrue(future3.isDone());

    String combined = Stream.of(future1, future2, future3)
        .map(CompletableFuture::join)
        .collect(Collectors.joining(" "));

    Assertions.assertEquals("Hello Java Future", combined);
  }

  @Test
  void handleException() throws ExecutionException, InterruptedException {
    String name = null;

    CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
          if (name == null) {
            throw new RuntimeException("Error!");
          }
          return "Hello, " + name;
        })
        .handle((s, t) -> {
          log("Error->" + t.getMessage());
          return s != null ? s : "Who are you!";
        });

    Assertions.assertEquals("Who are you!", completableFuture.get());
  }

  @Test
  void completeExceptionally() {
    CompletableFuture<String> completableFuture = new CompletableFuture<>();
    completableFuture.completeExceptionally(new RuntimeException("Calculation failed!"));
    Assertions.assertThrows(ExecutionException.class, completableFuture::get);
  }

  // CompletableFuture 의 모든 메소드는 Async postfix method가 있다
  @Test
  void supplyAsync_thenApplyAsync() throws ExecutionException, InterruptedException {
    CompletableFuture<String> future =
        CompletableFuture.supplyAsync(() -> "Hello").thenApplyAsync(s -> s + " World");
    Assertions.assertEquals("Hello World", future.get());
  }


}
