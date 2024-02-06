package jhkim105.rsupport.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class SpringJsonApplicationTests {

  @Autowired
  ObjectMapper objectMapper;

  @Test
  void jackson() throws Exception {
    SampleDateGroupedRecord sampleDateGroupedRecord =
        new SampleDateGroupedRecord(LocalDate.now(), LocalDateTime.now(), List.of(new Sample("sample1")));
    log.info(objectMapper.writeValueAsString(sampleDateGroupedRecord));
  }

}
