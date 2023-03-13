package jhkim105.tutorials.multitenancy.tenant.context;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@Component
@Slf4j
public class TenantFilter extends OncePerRequestFilter {
  public static final String X_TENANT_ID_HEADER = "X-Tenant-ID";

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    String tenantId = request.getHeader(X_TENANT_ID_HEADER);

    if (StringUtils.isNotBlank(tenantId)) {
      log.debug("TenantContext set, {}", tenantId);
      TenantContext.setTenantId(tenantId);
    }
    try {
      chain.doFilter(request, response);
    } finally {
      TenantContext.clear();
      log.debug("TenantContext deleted");
    }

  }
}
