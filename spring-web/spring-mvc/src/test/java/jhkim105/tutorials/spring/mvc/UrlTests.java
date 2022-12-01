package jhkim105.tutorials.spring.mvc;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


@Slf4j
@Disabled
class UrlTests {


  @Test
  void uriComponents() {
    UriComponents uriComponents = UriComponentsBuilder.fromUriString("http://localhost:8081/a/b/c").build();
    assertThat(uriComponents.getPath()).isEqualTo("/a/b/c");
    log.debug(uriComponents.getPath());
  }

  @Test
  void validUrl() {
    UrlValidator urlValidator= new UrlValidator(UrlValidator.ALLOW_LOCAL_URLS);
    assertThat(urlValidator.isValid("http://localhost/a/b/c")).isTrue();
    assertThat(urlValidator.isValid("localhost/a/b/c")).isFalse();
  }

}
