package jhkim105.tutorials.spring.files;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;


@Controller
@RequiredArgsConstructor
@Slf4j
public class StorageController {

  private final StorageService storageService;
  private final AppProperties appProperties;

  @GetMapping("/")
  public String home(Model model, HttpServletRequest request) {
    Stream<Path> pathStream = storageService.loadAll();
    List<String> filePaths = pathStream
        .map(p -> MvcUriComponentsBuilder
              .fromController(StorageController.class)
              .path(getPath(p))
              .build().toUri().toString())
        .collect(Collectors.toList());

    model.addAttribute("files", filePaths);
    return "home";
  }

  private String getPath(Path p) {
    return StringUtils.removeStart(p.toString(), appProperties.getStoragePath());
  }

  @GetMapping("/**")
  @ResponseBody
  public ResponseEntity<Resource> download(HttpServletRequest request, HttpServletResponse response,
      @RequestHeader Map<String, String> headers) throws IOException {

    headers.forEach((key, value) -> {
      log.debug("{}: {}", key, value);
    });

    String path = extractPath(request);
    log.debug("path: {}", path);
    Resource resource = storageService.loadAsResource(path);
    if (!resource.exists()) {
//      return ResponseEntity.notFound().build(); // /error 페이지로 이동안하고 바로 결과 리턴
      log.debug("Resource not found");
      response.sendError(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
            .filename(resource.getFilename(), StandardCharsets.UTF_8)
            .build().toString())
        .header(HttpHeaders.CONTENT_TYPE, contentType(resource))
        .body(resource);
  }

  private String contentType(Resource resource) {
    try {
      return Files.probeContentType(resource.getFile().toPath());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private String extractPath(HttpServletRequest request) {
    String path = (String)request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
    String matchPattern = (String)request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE); //
    return new AntPathMatcher().extractPathWithinPattern(matchPattern, path);
  }



  @PostMapping("/")
  public RedirectView upload(@RequestParam("file") MultipartFile multipartFile, String key, RedirectAttributes redirectAttributes) {
    log.info("key: {}", key);
    storageService.store(multipartFile, key);
    redirectAttributes.addFlashAttribute("message", "You successfully uploaded " +
        multipartFile.getOriginalFilename());
    return new RedirectView("/");
  }

}
