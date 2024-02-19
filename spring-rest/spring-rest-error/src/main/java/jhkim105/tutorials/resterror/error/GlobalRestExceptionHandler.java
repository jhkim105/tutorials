package jhkim105.tutorials.resterror.error;

import jhkim105.tutorials.resterror.exception.BaseException;
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
    public ResponseEntity<ErrorResponse> handleException(BaseException ex) {
        return handleException(HttpStatus.BAD_REQUEST, ex);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        return handleException(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    private ResponseEntity<ErrorResponse> handleException(HttpStatus status, Exception ex) {
        log.error("error!", ex);
        return new ResponseEntity<>(ErrorResponse.of(ex, status), status);
    }

}
