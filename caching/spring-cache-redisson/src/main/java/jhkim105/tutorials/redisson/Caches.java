package jhkim105.tutorials.redisson;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class Caches {
  public final static String CURRENT_DATE = "currentDate";
  public final static String CURRENT_DATE_RECORD = "currentDateRecord";
  public static Map<String, Duration> configMap() {
    Map<String, Duration> ttlMap = new HashMap<>();
    ttlMap.put(CURRENT_DATE, Duration.ofSeconds(10));
    return ttlMap;
  }

}
