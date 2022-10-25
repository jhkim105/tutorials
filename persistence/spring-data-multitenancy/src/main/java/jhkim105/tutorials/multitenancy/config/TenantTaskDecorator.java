package jhkim105.tutorials.multitenancy.config;

import jhkim105.tutorials.multitenancy.tenant.TenantContextHolder;
import org.springframework.core.task.TaskDecorator;

public class TenantTaskDecorator implements TaskDecorator {

  @Override
  public Runnable decorate(Runnable runnable) {
    String tenantId = TenantContextHolder.getTenantId();
    return () -> {
      try {
        TenantContextHolder.setTenantId(tenantId);
        runnable.run();
      } finally {
        TenantContextHolder.clear();
      }
    };
  }

}
