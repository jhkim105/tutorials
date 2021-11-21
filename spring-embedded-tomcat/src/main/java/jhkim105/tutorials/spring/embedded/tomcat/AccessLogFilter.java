package jhkim105.tutorials.spring.embedded.tomcat;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccessLogFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {
    log.info("set ignoreAccessLog to true");
    request.setAttribute("ignoreAccessLog", true);
    filterChain.doFilter(request, response);
  }
}
