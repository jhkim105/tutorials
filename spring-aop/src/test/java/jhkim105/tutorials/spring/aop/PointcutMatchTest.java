package jhkim105.tutorials.spring.aop;


import org.assertj.core.api.Assertions;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;


public class PointcutMatchTest {

    private void pointcutMatches(String expression, Boolean expected, Class<?> clazz, String methodName, Class<?>... args) throws Exception {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expression);

        Assertions.assertThat(pointcut.getClassFilter().matches(clazz)
                && pointcut.getMethodMatcher().matches(clazz.getMethod(methodName,  args), null)).isEqualTo(expected);
    }

}


