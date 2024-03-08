package jhkim105.rsupport.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;



@Slf4j
class JacksonTest {


  @Test
  void serializeAndDeserialize() throws Exception {
    var mapper = new ObjectMapper();
    
    var json = mapper.writeValueAsString(new Sample("sample 1"));
    var obj = mapper.readValue(json, Sample.class);
    log.info("obj->{}", obj);
  }

  @Test
  void serializeAndDeserializeRecordClass() throws Exception {
    var mapper = new ObjectMapper();
    var json = mapper.writeValueAsString(new SampleRecord("sample 1"));
    var obj = mapper.readValue(json, SampleRecord.class);
    log.info("obj->{}", obj);
  }

  @Test
  void serializeList() throws Exception {
    var sampleDateGroupedRecord = new SampleDateGroupedRecord(LocalDate.now(), LocalDateTime.now(), List.of(new Sample("sample1")));
    var mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    var result = mapper.writeValueAsString(sampleDateGroupedRecord);
    log.info("{}", result);
  }

  @Test
  void deserializeList() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    List<Sample> samples = List.of(new Sample("same 1"));
    String json = objectMapper.writeValueAsString(samples);
    log.info(json); // "[{\"name\":\"same 1\"}]";

    var result = objectMapper.readValue(json, new TypeReference<List<Sample>>() {});
    log.info("{}", result);
  }

}
