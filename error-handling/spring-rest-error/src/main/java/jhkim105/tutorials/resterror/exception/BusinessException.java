package jhkim105.tutorials.resterror.exception;

import jhkim105.tutorials.resterror.error.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

  private final ErrorCode errorCode;

  public BusinessException(ErrorCode errorCode) {
    super(errorCode.getCode());
    this.errorCode = errorCode;
  }

  @Override
  public synchronized Throwable fillInStackTrace() {
    return this;
  }
}
