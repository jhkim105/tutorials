package jhkim105.tutorials;

import jhkim105.tutorials.config.OutboundConfig.OutboundGateWay;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringIntegrationKafkaApplicationTests {

    @Autowired(required = false)
    private OutboundGateWay outboundGateway;

    @Autowired
    private KafkaSender kafkaSender;

    @Test
    void send() {
        outboundGateway.send("a");
        outboundGateway.send("b");
        kafkaSender.send(KafkaTopic.FOO, "c");
    }

}
