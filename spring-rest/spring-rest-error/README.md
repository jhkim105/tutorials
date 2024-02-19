


## Spring Boot Error 처리 관련 클래스
- DefaultErrorAttributes
- BasicErrorController


## Customize Error Response 

### @ControllerAdvice
```java
@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    private final ErrorAttributes errorAttributes;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex, WebRequest request) {
        return handleException(HttpStatus.BAD_REQUEST, request, ex);
    }

    protected ResponseEntity<Map<String, Object>> handleException(HttpStatus status, WebRequest request, Exception ex) {
        log.error("error!", ex);
        request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, status.value(), WebRequest.SCOPE_REQUEST);
        Map<String, Object> errorAttributeMap = errorAttributes.getErrorAttributes(request, ErrorAttributeOptions.of(Include.MESSAGE));
        String code;
        if (ex instanceof BaseException) {
            code = ((BaseException) ex).getErrorCode().getCode();
        } else {
            code = ErrorCode.FAIL.getCode();
        }
        errorAttributeMap.put("code", code);
        return new ResponseEntity<>(errorAttributeMap, new HttpHeaders(), status);
    }

}
```

### Customize ErrorAttributes
```java
@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

  @Override
  public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    String code;
    if (ex instanceof BaseException) {
      code = ((BaseException) ex).getErrorCode().getCode();
    } else {
      code = ErrorCode.FAIL.getCode();
    }
    request.setAttribute("code", code);
    return super.resolveException(request, response, handler, ex);
  }

  @Override
  public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
    Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
    errorAttributes.put("code", webRequest.getAttribute("code", WebRequest.SCOPE_REQUEST));
    return errorAttributes;
  }
}
```



