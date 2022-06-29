package jhkim105.tutorials.spring.mvc;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PathVariableController {

  @GetMapping("/path/uri/{uri}")
  // 404
  public Map<String, String> path(@PathVariable("uri") String uri) {
    Map<String, String> result = new HashMap<>();
    result.put("uri", uri);
    return result;
  }

  @GetMapping("/path/uri2/*")
  public Map<String, String> path2(HttpServletRequest request) {
    Map<String, String> result = new HashMap<>();
    String uri = extractWildcardPath(request, "path2");
    result.put("uri", uri);
    return result;
  }


  private static String extractWildcardPath(HttpServletRequest request, String prefix) {
    int index = request.getRequestURI().indexOf(prefix);
    return request.getRequestURI().substring(index + prefix.length() + 1);
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
    String path = extractWildcardPath(request, "path/file");
    result.put("file", file);
    result.put("path", path);
    return result;
  }

}
