package jhkim105.tutorials.multitenancy.config;

import jhkim105.tutorials.multitenancy.tenant.context.TenantContext;
import org.springframework.core.task.TaskDecorator;

public class TenantTaskDecorator implements TaskDecorator {

  @Override
  public Runnable decorate(Runnable runnable) {
    String tenantId = TenantContext.getTenantId();
    return () -> {
      try {
        TenantContext.setTenantId(tenantId);
        runnable.run();
      } finally {
        TenantContext.clear();
      }
    };
  }

}
