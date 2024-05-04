package jhkim105.tutorials.config;

import com.querydsl.codegen.ClassPathUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
/**
 * http://querydsl.com/static/querydsl/5.0.0/reference/html_single/
 * When Querydsl Q-types are initialized from multiple threads, deadlocks can occur, if the Q-types have circular dependencies.
 *
 * An easy to use solution is to initialize the classes in a single thread before they are used in different threads.
 *
 * The com.querydsl.codegen.ClassPathUtils class can be used for that like this:
 *
 *     ClassPathUtils.scanPackage(Thread.currentThread().getContextClassLoader(), packageToLoad);
 * Replace packageToLoad with the package of the classes you want to initialize.
 */
public class QuerydslInitializer implements ApplicationRunner {

  @Override
  public void run(ApplicationArguments args) throws Exception {
    log.info("Initialize Q-Types");
    ClassPathUtils.scanPackage(Thread.currentThread().getContextClassLoader(), "jhkim105.tutorials.domain");
  }
}
