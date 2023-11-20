package jhkim105.tutorials.spring.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class SampleLoggingAspect {


  @Before("execution(* jhkim105..*.*Controller.*(..)) && args(logging)")
  void log(JoinPoint joinPoint, Logging logging) {
    log.info("{}", logging);
  }

}
