package jhkim105.tutorials.spring.async.web;

import java.util.concurrent.CompletableFuture;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Async
public class AsyncController {

    @GetMapping("/result_async")
    @Async
    public CompletableFuture<String> getResultAsync() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture("Result is ready!");
    }

    @GetMapping("/result")
    public String getResult() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Result is ready!";
    }
}