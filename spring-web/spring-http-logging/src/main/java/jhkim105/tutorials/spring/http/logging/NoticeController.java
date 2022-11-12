package jhkim105.tutorials.spring.http.logging;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notices")
public class NoticeController {

  @GetMapping
  public ResponseEntity<Notice> get() {
    return ResponseEntity.ok(Notice.builder().title("Notice 01").build());
  }


  @Getter
  @ToString
  public static class Notice {
    String title;

    @Builder
    public Notice(String title) {
      this.title = title;
    }
  }

}
