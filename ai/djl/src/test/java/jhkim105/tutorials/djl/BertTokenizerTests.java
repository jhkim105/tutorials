package jhkim105.tutorials.djl;

import ai.djl.modality.nlp.bert.BertTokenizer;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class BertTokenizerTests {
  String text = "She stopped.  She said, \"Hello there,\" and then";
  @Test
  void tokenize() {
    BertTokenizer tokenizer = new BertTokenizer();
    List<String> tokens = tokenizer.tokenize(text);
    log.info("tokens: {}", tokens);
    log.info("tokens length: {}", tokens.size());
  }

}
