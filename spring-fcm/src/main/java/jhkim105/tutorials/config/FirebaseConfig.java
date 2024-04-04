package jhkim105.tutorials.config;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class FirebaseConfig {


  @Bean
  FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
    return FirebaseMessaging.getInstance(firebaseApp);
  }
  @Bean
  FirebaseApp firebaseApp(FirebaseProperties firebaseProperties) {
    // build 시 다음 에러 발생하여 조건 추가 (Test context 가 여러번 refresh 되는 경우)
    // Factory method 'firebaseApp' threw exception; nested exception is java.lang.IllegalStateException: FirebaseApp name [DEFAULT] already exists!
    if (FirebaseApp.getApps().isEmpty()) {
      log.info("Initializing FirebaseApp.");
      FirebaseOptions options = FirebaseOptions.builder()
          .setCredentials(googleCredentials(firebaseProperties))
          .build();
      return FirebaseApp.initializeApp(options);
    } else {
      log.info("FirebaseApp already initialized.");
      return FirebaseApp.getInstance();
    }
  }
  private GoogleCredentials googleCredentials(FirebaseProperties firebaseProperties) {
    try (InputStream is = firebaseProperties.getServiceAccount().getInputStream()) {
      return GoogleCredentials.fromStream(is);
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to load Google credentials.", e);
    }
  }


}
