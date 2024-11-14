package jhkim105.tutorials.kafka;


import jhkim105.tutorials.kafka.config.KafkaTopicConfig.Topics;
import jhkim105.tutorials.kafka.dto.Farewell;
import jhkim105.tutorials.kafka.dto.Greeting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(id = "multiGroup", topics = Topics.MULTITYPE)
@Slf4j
public class MultiTypeKafkaListener {
    @KafkaHandler
    public void handleGreeting(Greeting greeting) {
        log.info("Greeting received: {}", greeting);
    }

    @KafkaHandler
    public void handleFarewell(Farewell farewell) {
        log.info("Farewell received: {}", farewell);
    }

    @KafkaHandler(isDefault = true)
    public void unknown(Object object) {
        log.info("Unknown type received: {}", object);
    }
}
