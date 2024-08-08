package jhkim105.tutorials.error;

import jakarta.persistence.EntityNotFoundException;
import java.util.Map;
import java.util.NoSuchElementException;
import jhkim105.tutorials.base.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.error.ErrorAttributeOptions.Include;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
  @Autowired
  private ErrorAttributes errorAttributes;

  @ExceptionHandler({ BaseException.class})
  protected ResponseEntity<Object> handleBaseException(RuntimeException ex, WebRequest request) {
    return handleException(HttpStatus.BAD_REQUEST, request, ex);
  }

  @ExceptionHandler({ IllegalStateException.class })
  protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
    return handleException(HttpStatus.CONFLICT, request, ex);
  }

  @ExceptionHandler({ IllegalArgumentException.class })
  protected ResponseEntity<Object> handleBadRequest(RuntimeException ex, WebRequest request) {
    return handleException(HttpStatus.BAD_REQUEST, request, ex);
  }

  @ExceptionHandler({ NoSuchElementException.class, EntityNotFoundException.class})
  protected ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest request) {
    return handleException(HttpStatus.NOT_FOUND, request, ex);
  }

  @ExceptionHandler({ RuntimeException.class})
  protected ResponseEntity<Object> runtimeException(RuntimeException ex, WebRequest request) {
    return handleException(HttpStatus.BAD_REQUEST, request, ex);
  }

  protected ResponseEntity<Object> handleException(HttpStatus status, WebRequest request, Exception ex) {
    log.error("error!", ex);
    request.setAttribute("javax.servlet.error.status_code", status.value(), WebRequest.SCOPE_REQUEST);
    Map<String, Object> errorAttributeMap = errorAttributes.getErrorAttributes(request, ErrorAttributeOptions.of(Include.MESSAGE));
    String code = "";
    if (ex instanceof BaseException) {
      code = ((BaseException) ex).getCode();
    } else {
      code = ErrorCodes.FAIL;
    }
    errorAttributeMap.put("code", code);
    return new ResponseEntity<>(errorAttributeMap, new HttpHeaders(), status);
  }
}
