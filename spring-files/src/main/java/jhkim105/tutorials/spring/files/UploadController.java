package jhkim105.tutorials.spring.files;

import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UploadController {

  private final StorageService storageService;

  @GetMapping("/")
  public String list(Model model) {
    Stream<Path> pathStream = storageService.loadAll();

    model.addAttribute("files",
        pathStream
            .map(path -> MvcUriComponentsBuilder
                .fromMethodName(DownloadController.class, "download",
                    path.getFileName().toString()).build().toUri().toString())
            .collect(Collectors.toList()));

    return "upload-form";
  }

  @PostMapping("/")
  public RedirectView upload(@RequestParam("file")MultipartFile multipartFile, String key, RedirectAttributes redirectAttributes) {
    log.info("key: {}", key);
    storageService.store(multipartFile, key);
    redirectAttributes.addFlashAttribute("message", "You successfully uploaded " +
        multipartFile.getOriginalFilename());
    return new RedirectView("/");
  }


}
