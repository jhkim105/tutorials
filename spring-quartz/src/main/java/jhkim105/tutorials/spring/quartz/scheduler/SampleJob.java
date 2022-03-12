package jhkim105.tutorials.spring.quartz.scheduler;

import jhkim105.tutorials.spring.quartz.service.SampleService;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SampleJob implements Job {

    private final SampleService sampleService;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        sampleService.doSomething();
    }

}