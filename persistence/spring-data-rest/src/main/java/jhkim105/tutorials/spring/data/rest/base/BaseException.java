package jhkim105.tutorials.spring.data.rest.base;

import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.support.MessageSourceAccessor;

@Getter
public class BaseException extends RuntimeException {
  private static final long serialVersionUID = 5323461248670767109L;
  private final String code;
  private Object[] messageArgs;

  public BaseException() {
    this(ErrorCodes.FAIL);
  }

  public BaseException(String code, Throwable cause) {
    super(code, cause);
    this.code = code;
  }

  public BaseException(String code) {
    super(code);
    this.code = code;
  }

  public BaseException(String code, Object[] messageArgs) {
    this(code);
    this.messageArgs = messageArgs;
  }

  public BaseException(String code, String debugMessage, Throwable cause) {
    super(String.format("%s %s",code, debugMessage, cause));
    this.code = code;
  }


  public BaseException(String code, String debugMessage) {
    super(String.format("%s %s",code, debugMessage));
    this.code = code;
  }


  public String getExceptionDebugMessage() {
    return this.getMessage();
  }

  public String getExceptionMessage(MessageSourceAccessor messages) {
    if (messageArgs == null || ArrayUtils.isEmpty(messageArgs)) {
      return messages.getMessage("error." + code);
    } else {
      return messages.getMessage("error." + code, messageArgs);
    }
  }
}
