package jhkim105.tutorials.distributed_lock;

import java.util.HashMap;
import java.util.Map;
import jhkim105.tutorials.distributed_lock.aop.DistributedLock;
import org.springframework.stereotype.Service;


@Service
public class CounterService {

  private Map<String, Counter> counterMap = new HashMap<>();

  @DistributedLock(key = "#name", waitTime = 10, leaseTime = 1)
  public void increase(String name) {
    Counter counter = counterMap.get(name);
    if (counter == null) {
      counter = new Counter();
      counterMap.put(name, counter);
    }
    counter.increase();
  }

  public Counter getCounter(String name) {
    return counterMap.get(name);
  }


}
