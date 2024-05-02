package jhkim105.tutorials.resterror.error;

import jakarta.servlet.http.HttpServletRequest;
import jhkim105.tutorials.resterror.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalRestExceptionHandler {

    @Value("${stacktrace.enabled:false}")
    private boolean stackTraceEnabled;

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(BusinessException ex, HttpServletRequest request) {
        if (isStackTrace(request)) {
            log.warn("error!", ex);
        } else {
            log.warn("error: {}", ex.getMessage());
        }

        return handleException(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest request) {
        if (isStackTrace(request)) {
            log.error("error!", ex);
        } else {
            log.error("error: {}", ex.getMessage());
        }
        return handleException(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    private ResponseEntity<ErrorResponse> handleException(HttpStatus status, Exception ex) {
        return new ResponseEntity<>(ErrorResponse.of(ex, status), status);
    }


    private boolean isStackTrace(HttpServletRequest request) {
        return getBooleanParameter(request, "trace") || stackTraceEnabled;
    }
    private boolean getBooleanParameter(HttpServletRequest request, String parameterName) {
        String parameter = request.getParameter(parameterName);
        if (parameter == null) {
            return false;
        } else {
            return !"false".equalsIgnoreCase(parameter);
        }
    }

}
