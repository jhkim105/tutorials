package jhkim105.tutorials.multitenancy.tenant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

public class TenantInterceptor implements HandlerInterceptor {

  public static final String HEADER_TENANT_ID = "X-TENANT-ID";
  public static final String PARAM_TENANT_ID = "tenantId";

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String tenantId = request.getHeader(HEADER_TENANT_ID);
    if (StringUtils.hasText(tenantId)) {
      TenantContextHolder.setTenantId(tenantId);
    }
    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {
    TenantContextHolder.clear();
  }
}
