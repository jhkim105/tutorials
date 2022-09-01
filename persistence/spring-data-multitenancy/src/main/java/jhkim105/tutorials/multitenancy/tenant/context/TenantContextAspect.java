package jhkim105.tutorials.multitenancy.tenant.context;

import java.lang.reflect.Method;
import jhkim105.tutorials.multitenancy.master.domain.Tenant;
import jhkim105.tutorials.multitenancy.master.service.TenantService;
import jhkim105.tutorials.multitenancy.tenant.TenantContextHolder;
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
public class TenantContextAspect {

  private final TenantService tenantService;


  @Around("@annotation(jhkim105.tutorials.multitenancy.tenant.context.TenantContext)")
  public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    TenantContext tenantContext = method.getAnnotation(TenantContext.class);
    String key = (String)getDynamicValue(signature.getParameterNames(), joinPoint.getArgs(), tenantContext.key());
    log.info("key: {}", key);
    Tenant tenant = tenantService.findById(key);
    try {
      if (tenant != null) {
        TenantContextHolder.setTenantId(tenant.getId());
      }
      log.info("TenantContext created");
      final Object proceed = joinPoint.proceed();
      return proceed;
    } finally {
      TenantContextHolder.clear();
      log.info("TenantContext deleted");
    }

  }

  public Object getDynamicValue(String[] parameterNames,
      Object[] args, String key) {
    ExpressionParser parser = new SpelExpressionParser();
    StandardEvaluationContext context = new
        StandardEvaluationContext();

    for (int i = 0; i < parameterNames.length; i++) {
      context.setVariable(parameterNames[i], args[i]);
    }
    return parser.parseExpression(key).getValue(context,
        Object.class);
  }


}
