package jhkim105.tutorials.spring.cloud.function.aws.functional;

import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cloud.function.context.FunctionRegistration;
import org.springframework.cloud.function.context.FunctionType;
import org.springframework.cloud.function.context.FunctionalSpringApplication;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;

@SpringBootConfiguration
public class SpringCloudFunctionFunctionalBeanDefinitionApplication implements ApplicationContextInitializer<GenericApplicationContext> {
  Logger log = LoggerFactory.getLogger(SpringCloudFunctionFunctionalBeanDefinitionApplication.class);

  public static void main(String[] args) {
    FunctionalSpringApplication.run(SpringCloudFunctionFunctionalBeanDefinitionApplication.class, args);
  }

  public Function<String, String> uppercase() {
    return param -> {
      System.out.println("param:" + param);
      return param.toUpperCase();};
  }

  @Override
  public void initialize(GenericApplicationContext context) {
    log.debug("initialize");
    context.registerBean("uppercase", FunctionRegistration.class,
        () -> new FunctionRegistration(uppercase())
            .type(FunctionType.from(String.class).to(String.class)));

  }
}
