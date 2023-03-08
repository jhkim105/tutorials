package jhkim105.tutorials.multitenancy.tenant;

import static jhkim105.tutorials.multitenancy.master.domain.Tenant.DEFAULT_TENANT_ID;

import jhkim105.tutorials.multitenancy.tenant.context.TenantContextHolder;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.util.StringUtils;

public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

  @Override
  public String resolveCurrentTenantIdentifier() {
    String tenant = TenantContextHolder.getTenantId();
    return StringUtils.hasText(tenant) ? tenant : DEFAULT_TENANT_ID;
  }

  @Override
  public boolean validateExistingCurrentSessions() {
    return true;
  }
}
