package jhkim105.tutorials.resterror.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import jhkim105.tutorials.resterror.exception.BusinessException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;


//@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

  @Override
  public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    super.resolveException(request, response, handler, ex);
    String code;
    if (ex instanceof BusinessException) {
      code = ((BusinessException) ex).getErrorCode().getCode();
    } else {
      code = ErrorCode.UNKNOWN.getCode();
    }
    request.setAttribute("code", code);
    return null;
  }

  @Override
  public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
    Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
    errorAttributes.put("code", webRequest.getAttribute("code", WebRequest.SCOPE_REQUEST));

    return errorAttributes;
  }
}
