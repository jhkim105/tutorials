package jhkim105.tutorials.spring.files;

import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
public class UploadController {

  private final StorageService storageService;

  @GetMapping("/")
  public String list(Model model) {
    Stream<Path> pathStream = storageService.loadAll();

    model.addAttribute("files",
        pathStream
            .map(path -> MvcUriComponentsBuilder
                .fromMethodName(UploadController.class, "download",
                    path.getFileName().toString()).build().toUri().toString())
            .collect(Collectors.toList()));

    return "upload-form";
  }

  @GetMapping("/files/{filename:.+}")
  @ResponseBody
  public ResponseEntity<Resource> download(@PathVariable String filename) {
    Resource file = storageService.loadAsResource(filename);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + file.getFilename() + "\"")
        .body(file);
  }

  @PostMapping("/")
  public RedirectView upload(@RequestParam("file")MultipartFile multipartFile, RedirectAttributes redirectAttributes) {
    storageService.store(multipartFile);
    redirectAttributes.addFlashAttribute("message", "You successfully uploaded " +
        multipartFile.getOriginalFilename());
    return new RedirectView("/");
  }

  @ExceptionHandler(StorageFileNotFoundException.class)
  public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException ex) {
    return ResponseEntity.notFound().build();
  }

}
