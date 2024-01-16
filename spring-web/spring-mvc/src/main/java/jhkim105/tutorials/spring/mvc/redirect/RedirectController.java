package jhkim105.tutorials.spring.mvc.redirect;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/redirect")
@Slf4j
public class RedirectController {

  @GetMapping("/redirectWithRedirectView")
  public RedirectView redirectWithRedirectView(RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("flashAttribute", "flashAttributeValue");
    redirectAttributes.addAttribute("attribute", "attributeValue");
    return new RedirectView("redirectUrl");
  }


  @GetMapping("/redirectUrl")
  @ResponseBody
  public ResponseEntity<String> redirectUrl(@RequestParam String attribute, @ModelAttribute("flashAttribute") String flashAttribute, HttpServletRequest request) {
    Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
    if (inputFlashMap != null) {
      log.info("flashMap.flashAttribute:{}", inputFlashMap.get("flashAttribute"));
    }
    return ResponseEntity.ok(String.format("attribute:%s, flashAttribute:%s", attribute, flashAttribute));
  }

}
