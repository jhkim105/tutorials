package jhkim105.tutorials.spring.aop;


import jhkim105.tutorials.spring.aop.SampleController.SampleRequest;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;


@Slf4j
class PointcutMatchTest {

  private void pointcutMatches(String expression, Boolean expected, Class<?> clazz, String methodName, Class<?>... args)
      throws Exception {
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    pointcut.setExpression(expression);

    Assertions.assertThat(pointcut.getClassFilter().matches(clazz)
        && pointcut.getMethodMatcher().matches(clazz.getMethod(methodName, args), null)).isEqualTo(expected);
  }


  @Test
  void testExecution() throws NoSuchMethodException {
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    pointcut.setExpression("execution(* jhkim105.tutorials..*.*Controller.*(..))");

    Class clazz = SampleController.class;
    boolean methodMatches = pointcut.getMethodMatcher().matches(clazz.getMethod("save", SampleRequest.class), clazz);
    log.info("methodMatches: {}", methodMatches);

    Assertions.assertThat(methodMatches).isEqualTo(true);
  }



  @Test
  void testWithin() {
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    pointcut.setExpression("within(jhkim105.tutorials..*.*Controller)");

    Assertions.assertThat(pointcut.getClassFilter().matches(SampleController.class)).isEqualTo(true);
  }

}


