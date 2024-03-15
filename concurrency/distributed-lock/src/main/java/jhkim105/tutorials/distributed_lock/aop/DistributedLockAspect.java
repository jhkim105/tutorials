package jhkim105.tutorials.distributed_lock.aop;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RedissonClient;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;


@Slf4j
@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@RequiredArgsConstructor
public class DistributedLockAspect {

  private final ExpressionParser parser = new SpelExpressionParser();

  private final RedissonClient redissonClient;

  @Around("@annotation(distributedLock)")
  public Object execute(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) throws Throwable {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    String key = parser.parseExpression(distributedLock.key()).getValue(
        standardEvaluationContext(signature.getParameterNames(), joinPoint.getArgs()), String.class);
    log.info("Attempting to acquire lock for key: {}", key);
    var lock = redissonClient.getLock(key);
    try {
      if (lock.tryLock(distributedLock.waitTime(), distributedLock.leaseTime(), TimeUnit.SECONDS)) {
        return joinPoint.proceed();
      } else {
        log.error("Failed to acquire lock for key: {}", key);
        throw new IllegalStateException("Failed to acquire lock");
      }
    } finally {
      // Caused by: java.lang.IllegalMonitorStateException: attempt to unlock lock, not locked by current thread by node id: ae996278-60b2-466e-b26a-d206012a56f4 thread-id: 73
      // 위 에러 방지위해 아래 조건 필요
      if(lock.isHeldByCurrentThread()) {
        lock.unlock();
      }
    }
  }

  private StandardEvaluationContext standardEvaluationContext(String[] parameterNames, Object[] args) {
    StandardEvaluationContext context = new StandardEvaluationContext();

    for (int i = 0; i < parameterNames.length; i++) {
      context.setVariable(parameterNames[i], args[i]);
    }
    return context;
  }
}
