package jhkim105.tutorials.spring.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class MyAspect {

  private final ExpressionParser parser = new SpelExpressionParser();
  @Around("@annotation(myAnnotation)")
  public Object doSomething(ProceedingJoinPoint joinPoint, MyAnnotation myAnnotation) throws Throwable {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    StandardEvaluationContext context = getStandardEvaluationContext(signature.getParameterNames(),
        joinPoint.getArgs());

    String key = parser.parseExpression(myAnnotation.key()).getValue(context, String.class);
    log.info("key: {}", key);
    return joinPoint.proceed();
  }

  private StandardEvaluationContext getStandardEvaluationContext(String[] parameterNames, Object[] args) {
    StandardEvaluationContext context = new StandardEvaluationContext();

    for (int i = 0; i < parameterNames.length; i++) {
      context.setVariable(parameterNames[i], args[i]);
    }
    return context;
  }

}
