package jhkim105.tutorials.resterror.error;

import jhkim105.tutorials.resterror.exception.BusinessException;
import org.springframework.http.HttpStatus;

public record ErrorResponse(
    int status,
    String code,
    String message
) {

  public static ErrorResponse of(Exception ex, HttpStatus status) {
    String code;
    if (ex instanceof BusinessException) {
      code = ((BusinessException) ex).getErrorCode().getCode();
    } else {
      code = ErrorCode.UNKNOWN.getCode();
    }
    return new ErrorResponse(status.value(), code, ex.getMessage());
  }
}
