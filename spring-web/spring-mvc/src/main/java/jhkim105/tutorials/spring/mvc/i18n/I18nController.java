package jhkim105.tutorials.spring.mvc.i18n;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/i18n")
@RequiredArgsConstructor
public class I18nController {

  private MessageSourceAccessor messageSourceAccessor;

  @Autowired
  public void setMessageSource(MessageSource messageSource) {
    this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
  }

  @GetMapping
  public String get() {
    return "i18n_";
  }

  @GetMapping("/message/{code}")
  @ResponseBody
  public String getMessage(@PathVariable String code) {
    return messageSourceAccessor.getMessage(code);
  }
}
