package jhkim105.tutorials.resterror.error;

import jhkim105.tutorials.resterror.exception.BaseException;
import org.springframework.http.HttpStatus;

public record ErrorResponse(
    int status,
    String code,
    String message
) {

  public static ErrorResponse of(Exception ex, HttpStatus status) {
    String code;
    if (ex instanceof BaseException) {
      code = ((BaseException) ex).getErrorCode().getCode();
    } else {
      code = ErrorCode.UNKNOWN.getCode();
    }
    return new ErrorResponse(status.value(), code, ex.getMessage());
  }
}
