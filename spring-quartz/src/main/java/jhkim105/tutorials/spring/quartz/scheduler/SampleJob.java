package jhkim105.tutorials.spring.quartz.scheduler;

import jhkim105.tutorials.spring.quartz.service.SampleService;
import lombok.RequiredArgsConstructor;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SampleJob extends QuartzJobBean {

    private final SampleService sampleService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        sampleService.doSomething();
    }

}