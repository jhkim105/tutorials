package jhkim105.tutorials.gpt_tokenizer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;


@Slf4j
class GPT2TokenizerTest {



  @Test
  void testEncoding() {
    GPT2Tokenizer tokenizer = GPT2Tokenizer.fromPretrained("gpt2");
    log.info("tokens: {}", tokenizer.encode("안녕하세요. 좋은 아침이에요.").size());
    log.info("tokens: {}", tokenizer.encode("도움말:한국어 검색결과만 검색합니다. 환경설정에서 검색 언어를 지정할 수 있습니다.").size());
    log.info("tokens: {}", tokenizer.encode("가").size());
    log.info("tokens: {}", tokenizer.encode("가나..").size());
  }

}