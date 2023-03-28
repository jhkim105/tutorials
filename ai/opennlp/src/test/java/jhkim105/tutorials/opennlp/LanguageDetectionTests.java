package jhkim105.tutorials.opennlp;

import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import opennlp.tools.langdetect.Language;
import opennlp.tools.langdetect.LanguageDetector;
import opennlp.tools.langdetect.LanguageDetectorME;
import opennlp.tools.langdetect.LanguageDetectorModel;
import org.junit.jupiter.api.Test;

/**
 * <a href="https://opennlp.apache.org/docs/2.1.1/manual/opennlp.html#tools.langdetect">OpenNLP</a>
 */
@Slf4j
class LanguageDetectionTests {

  @Test
  void languageDetection() throws IOException {
    InputStream inputStream = getClass().getResourceAsStream("/opennlp/models/langdetect-183.bin");
    LanguageDetectorModel model = new LanguageDetectorModel(inputStream);

    LanguageDetector languageDetector = new LanguageDetectorME(model);

    Language bestLanguage = languageDetector.predictLanguage("안녕");
    log.info("Best language: {}, Best language confidence: {}", bestLanguage.getLang(), bestLanguage.getConfidence());
    Language[] languages = languageDetector.predictLanguages("안녕");
    log.info("Predicted languages..");
    for(Language language:languages){
      log.info("{} confidence: {}", language.getLang(), language.getConfidence());
    }
  }

}
