package jhkim105.rsupport.json;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;


@Slf4j
class JsonUnwrapTest {


  @Test
  // @JsonUnwrapped은 생성자에 적용되어야 하는데, record 클래스의 경우 자동 생성된 생성자에 적용할 수 없다.
  void deserializeError() throws Exception {
    var meeting = new Meeting("id01", "meeting 01");
    var meetingResponse = new MeetingCreateResponse(meeting);

    var mapper = new ObjectMapper();
    var json = mapper.writeValueAsString(meetingResponse);
    log.info(json);

    assertThatThrownBy(() -> {
      var obj = mapper.readValue(json, MeetingCreateResponse.class);
    }).isInstanceOf(InvalidDefinitionException.class)
        .hasMessage("Cannot define Creator property \"meeting\" as `@JsonUnwrapped`: combination not yet supported\n"
            + " at [Source: (String)\"{\"id\":\"id01\",\"name\":\"meeting 01\"}\"; line: 1, column: 1]");
  }

  @Test
  void deserializeOk() throws Exception {
    var meeting = new Meeting("id01", "meeting 01");
    var meetingResponse = new MeetingUpdateResponse(meeting);

    var mapper = new ObjectMapper();
    var json = mapper.writeValueAsString(meetingResponse);
    log.info(json);

    var obj = mapper.readValue(json, MeetingUpdateResponse.class);
    log.info("{}", obj);
    assertThat(meetingResponse).isEqualTo(obj);
  }


  record MeetingCreateResponse(@JsonUnwrapped Meeting meeting){}
  record Meeting(String id, String name) {}

  record MeetingUpdateResponse(@JsonUnwrapped Meeting meeting){

    @JsonCreator
    public MeetingUpdateResponse(@JsonProperty("id") String id,
        @JsonProperty("name") String name) {
      this(new Meeting(id, name));
    }
  }
}
