package jhkim105.tutorials.resterror.error;

import jhkim105.tutorials.resterror.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalRestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(BusinessException ex) {
        log.warn("error: {}", ex.getMessage());
        return handleException(HttpStatus.BAD_REQUEST, ex);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error("error!", ex);
        return handleException(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    private ResponseEntity<ErrorResponse> handleException(HttpStatus status, Exception ex) {
        return new ResponseEntity<>(ErrorResponse.of(ex, status), status);
    }

}
