package jhkim105.tutorials.resterror.error;

import jakarta.servlet.RequestDispatcher;
import java.util.Map;
import jhkim105.tutorials.resterror.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.error.ErrorAttributeOptions.Include;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

//@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    private final ErrorAttributes errorAttributes;

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> handleException(Exception ex, WebRequest request) {
        return handleException(HttpStatus.INTERNAL_SERVER_ERROR, request, ex);
    }

    private ResponseEntity<Map<String, Object>> handleException(HttpStatus status, WebRequest request, Exception ex) {
        request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, status.value(), WebRequest.SCOPE_REQUEST);
        Map<String, Object> errorAttributeMap = errorAttributes.getErrorAttributes(request, ErrorAttributeOptions.of(Include.MESSAGE));
        String code;
        if (ex instanceof BusinessException) {
            log.warn("error: {}", ex.getMessage());
            code = ((BusinessException) ex).getErrorCode().getCode();
        } else {
            log.error("error!", ex);
            code = ErrorCode.UNKNOWN.getCode();
        }
        errorAttributeMap.put("code", code);
        return new ResponseEntity<>(errorAttributeMap, new HttpHeaders(), status);
    }

}
