package jhkim105.tutorials.resterror.exception;

import jhkim105.tutorials.resterror.error.ErrorCode;

public class ResourceNotFoundException extends BusinessException {
  public ResourceNotFoundException() {
    super(ErrorCode.RESOURCE_NOT_FOUND);
  }

}
