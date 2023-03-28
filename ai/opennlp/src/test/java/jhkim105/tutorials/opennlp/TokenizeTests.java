package jhkim105.tutorials.opennlp;


import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import org.junit.jupiter.api.Test;

/**
 * <a href="https://opennlp.apache.org/docs/">ApacheNLP Docs</a>
 * <a href="https://opennlp.sourceforge.net/models-1.5/">Model</a>
 */
@Slf4j
class TokenizeTests {

  String text = "She stopped.  She said, \"Hello there,\" and then";

  @Test
    // model 사용
  void tokenizeUsingTokenizerME() throws IOException {
    InputStream inputStream = getClass().getResourceAsStream("/opennlp/models/en-token.bin");
    TokenizerModel model = new TokenizerModel(inputStream);
    TokenizerME tokenizer = new TokenizerME(model);
    // token - words, numbers or punctuation marks
    List<String> tokens = Arrays.asList(tokenizer.tokenize(text));
    log.debug("{}", tokens);
    log.debug("{}", tokens.size());
    assertThat(tokens).hasSize(13);
    assertThat(tokens).contains("She", "stopped", ".", "She", "said", ",", "\"", "Hello", "there", ",", "\"", "and", "then");
  }

  @Test
    // 공백으로 구분
  void tokenizeUsingWhitespaceTokenizer() {
    WhitespaceTokenizer tokenizer = WhitespaceTokenizer.INSTANCE;
    List<String> tokens = Arrays.asList(tokenizer.tokenize(text));
    log.debug("{}", tokens);
    log.debug("{}", tokens.size());
    assertThat(tokens).contains("She", "stopped.", "She", "said,", "\"Hello", "there,\"", "and", "then");
  }

  @Test
    // words, numbers, and punctuation marks
  void tokenizeUsingSimpleTokenizer() {
    SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
    List<String> tokens = Arrays.asList(tokenizer.tokenize(text));
    log.debug("{}", tokens);
    log.debug("{}", tokens.size());
    assertThat(tokens).contains("She", "stopped", ".", "She", "said", ",", "\"", "Hello", "there", ",", "\"", "and", "then");
  }

}
