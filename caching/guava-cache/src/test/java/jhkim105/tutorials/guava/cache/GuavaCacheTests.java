package jhkim105.tutorials.guava.cache;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalCause;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.Weigher;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;


@Slf4j
class GuavaCacheTests {

  @Test
  void createCache() {
    CacheLoader<String, String> loader = new CacheLoader<>() {
      @Override
      public String load(String key) {
        return key.toUpperCase();
      }
    };

    LoadingCache<String, String> cache = CacheBuilder.newBuilder().build(loader);

    assertEquals(0, cache.size());
    assertEquals("HELLO", cache.getUnchecked("hello"));
    assertEquals(1, cache.size());
  }

  @Test
  void evictBySizeAndRemovalListener() {
    CacheLoader<String, String> loader = new CacheLoader<>() {
      @Override
      public String load(final String key) {
        return key.toUpperCase();
      }
    };

    RemovalListener<String, String> listener = n -> {
      if (n.wasEvicted()) {
        String cause = n.getCause().name();
        assertEquals(RemovalCause.SIZE.toString(),cause);
      }
    };

    LoadingCache<String, String> cache;
    cache = CacheBuilder.newBuilder()
        .maximumSize(3)
        .removalListener(listener)
        .build(loader);

    cache.getUnchecked("first");
    cache.getUnchecked("second");
    cache.getUnchecked("third");
    cache.getUnchecked("last");
    assertEquals(3, cache.size());

  }

  @Test
  void evictByTime() throws InterruptedException, ExecutionException {
    CacheLoader<String, String> loader = new CacheLoader<>() {
      @Override
      public String load(String key) {
        return key.toUpperCase();
      }
    };

    RemovalListener<String, String> listener = n -> {
      log.info("key: {}, value: {}, wasEvicted: {}", n.getKey(), n.getValue(), n.wasEvicted());
      if (n.wasEvicted()) {
        String cause = n.getCause().name();
        assertEquals(RemovalCause.EXPIRED.toString(),cause);
      }
    };


    LoadingCache<String, String> cache = CacheBuilder.newBuilder()
        .expireAfterAccess(100, TimeUnit.MILLISECONDS)
        .maximumSize(10)
        .removalListener(listener)
        .build(loader);

    cache.getUnchecked("hello");
    assertEquals(1, cache.size());

    Thread.sleep(300);

    cache.getUnchecked("test");
    assertEquals(1, cache.size());
    assertNull(cache.getIfPresent("hello"));
    Thread.sleep(300);
    cache.cleanUp();
    assertNull(cache.getIfPresent("test"));
  }

  @Test
  void evictByWeight() {
    CacheLoader<String, String> loader;
    loader = new CacheLoader<>() {
      @Override
      public String load(String key) {
        return key.toUpperCase();
      }
    };

    Weigher<String, String> weighByLength;
    weighByLength = (key, value) -> value.length();

    LoadingCache<String, String> cache = CacheBuilder.newBuilder()
        .maximumWeight(16)
        .weigher(weighByLength)
        .build(loader);

    cache.getUnchecked("first");
    cache.getUnchecked("second");
    cache.getUnchecked("third");
    cache.getUnchecked("last");
    assertEquals(3, cache.size());
    assertNull(cache.getIfPresent("first"));
    assertEquals("LAST", cache.getIfPresent("last"));
  }

}
