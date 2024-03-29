package jhkim105.tutorials.spring.mvc.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

@RestController
public class PathVariableController {

  @GetMapping("/path/uri/{uri}")
  // 404
  public Map<String, String> path(@PathVariable("uri") String uri) {
    Map<String, String> result = new HashMap<>();
    result.put("uri", uri);
    return result;
  }

  @GetMapping("/path/uri2/**")
  public Map<String, String> path2(HttpServletRequest request) {
    Map<String, String> result = new HashMap<>();
    String uri = extractPath(request);
    result.put("uri", uri);
    return result;
  }

  private String extractPath(HttpServletRequest request) {
    String path = (String)request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
    String matchPattern = (String)request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE); //
    return new AntPathMatcher().extractPathWithinPattern(matchPattern, path);
  }

  @GetMapping("/path/file/{file:.+}")
  public Map<String, String> file(@PathVariable String file) {
    Map<String, String> result = new HashMap<>();
    result.put("file", file);
    return result;
  }


  @GetMapping("/path/file/**/{file:.+}")
  public Map<String, String> file2(@PathVariable String file, HttpServletRequest request) {
    Map<String, String> result = new HashMap<>();
    String path = extractPath(request);
    result.put("file", file);
    result.put("path", path);
    return result;
  }

  @GetMapping("/path/enum/{storageType}")
  public StorageType storageType(@PathVariable StorageType storageType) {
    return storageType;
  }

  @GetMapping("/path/enum2/{idpType}")
  public IdpType idpType(@PathVariable IdpType idpType) {
    return idpType;
  }

  public enum IdpType {
    GOOGLE,FACEBOOK
  }

}
