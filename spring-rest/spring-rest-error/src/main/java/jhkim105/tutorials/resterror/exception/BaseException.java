package jhkim105.tutorials.resterror.exception;

import jhkim105.tutorials.resterror.error.ErrorCode;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

  private final ErrorCode errorCode;

  public BaseException(ErrorCode errorCode) {
    super(errorCode.getCode());
    this.errorCode = errorCode;
  }

}
