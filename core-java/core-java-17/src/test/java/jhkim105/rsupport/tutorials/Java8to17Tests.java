package jhkim105.rsupport.tutorials;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import jhkim105.rsupport.tutorials.record.Member;
import org.junit.jupiter.api.Test;

class Java8to17Tests {

  @Test
  void varKeyword() {
    var str = "This is from java 10";
    System.out.println(str);
  }

  @Test
  void textBlock() {
    String value = """
        Multi-line
        Text
        """;
    assertThat(value.split("\n")[0]).isEqualTo("Multi-line");
  }

  @Test
  void recordClass() {
    var sample = new Member(21);
    System.out.println(sample);
    assertThat(sample.age()).isEqualTo(21);
  }

  @Test
  void enhancedInstanceOfOperator() {
    var obj = new Member("member01", 10);
    if (obj instanceof Member) { // 타입 체크와 변환을 동시에
      var memberName = obj.name();
      assertThat(memberName).isEqualTo("member01");
    }
  }

  @Test
  void enhancedSwitchExpression() {
    var day = Day.MONDAY;
    int numLetters = switch (day) {
      case MONDAY, FRIDAY   -> 6;
      case TUESDAY          -> 7;
      case THURSDAY         -> 8;
      case WEDNESDAY        -> 9;
      default               -> {
        System.out.println("default");
        yield day.toString().length();
      } // 단일표현식으로 처리되지 않는 경우 yield 로 값 반환하고 흐름 종료
    };
    assertThat(numLetters).isEqualTo(6);
  }


}

enum Day {
  MONDAY,TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}

