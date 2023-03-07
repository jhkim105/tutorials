package jhkim105.tutorials.spring.aop;

import java.lang.reflect.Method;
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
  @Around("@annotation(jhkim105.tutorials.spring.aop.MyAnnotation)")
  public Object doSomething(ProceedingJoinPoint joinPoint) throws Throwable {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    MyAnnotation myAnnotation = method.getAnnotation(MyAnnotation.class);
    String key = (String)getDynamicValue(signature.getParameterNames(), joinPoint.getArgs(), myAnnotation.key());
    log.info("key: {}", key);

    return joinPoint.proceed();
  }

  public Object getDynamicValue(String[] parameterNames, Object[] args, String key) {
    StandardEvaluationContext context = new StandardEvaluationContext();

    for (int i = 0; i < parameterNames.length; i++) {
      context.setVariable(parameterNames[i], args[i]);
    }
    return parser.parseExpression(key).getValue(context, Object.class);
  }
}
