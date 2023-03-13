package jhkim105.tutorials.multitenancy.tenant.context;

import java.lang.reflect.Method;
import jhkim105.tutorials.multitenancy.master.domain.Tenant;
import jhkim105.tutorials.multitenancy.master.service.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;


@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class TenantAspect {

  private final TenantService tenantService;
  private final ExpressionParser parser = new SpelExpressionParser();

  @Around("@annotation(jhkim105.tutorials.multitenancy.tenant.context.TenantSetter)")
  public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();

    TenantSetter tenantSetter = method.getAnnotation(TenantSetter.class);
    String key = (String)getDynamicValue(signature.getParameterNames(), joinPoint.getArgs(), tenantSetter.key());
    log.debug("key: {}", key);
    Tenant tenant = tenantService.findById(key);

    try {
      if (tenant != null) {
        TenantContext.setTenantId(tenant.getId());
        log.debug("TenantContext created");
      }
      return joinPoint.proceed();
    } finally {
      TenantContext.clear();
      log.debug("TenantContext deleted");
    }

  }

  private Object getDynamicValue(String[] parameterNames, Object[] args, String key) {
    StandardEvaluationContext context = new StandardEvaluationContext();
    for (int i = 0; i < parameterNames.length; i++) {
      context.setVariable(parameterNames[i], args[i]);
    }

    return parser.parseExpression(key).getValue(context, Object.class);
  }


}
