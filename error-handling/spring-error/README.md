

## Default Error Handling
- BasicErrorController

### printStackTrace
- StandardWrapperValve.invoke() 에서 BadRequestException 이 아닌 경우에 log.error(msg, e) 를 실행함
```text
    } catch (ServletException e) {
            Throwable rootCause = StandardWrapper.getRootCause(e);
            if (!(rootCause instanceof BadRequestException)) {
                container.getLogger().error(sm.getString("standardWrapper.serviceExceptionRoot", wrapper.getName(),
                        context.getName(), e.getMessage()), rootCause);
            }
            throwable = e;
            exception(request, response, e);
   }        
```

### Properties
- server.error.include-stacktrace(never, always, on-param): html 응답에 stack trace 포함 여부
- server.error.include-message(never, always, on-param): 응답에 message 포함 여부
- server.error.include-exception(true, false): application/json 응답에 exception 포함 여부
  - "exception": "java.lang.IllegalStateException"

