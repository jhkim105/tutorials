package jhkim105.tutorials.resterror.error;


import lombok.Getter;

@Getter
public enum ErrorCode {
  UNKNOWN("999"),
  RESOURCE_NOT_FOUND("901")
  ;

  private final String code;

  ErrorCode(String code) {
    this.code = code;
  }
}
