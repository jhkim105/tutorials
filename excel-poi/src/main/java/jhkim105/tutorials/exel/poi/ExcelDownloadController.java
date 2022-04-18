package jhkim105.tutorials.exel.poi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExcelDownloadController {


  @GetMapping("/download")
  public ResponseEntity<InputStreamResource> download() {
    List<List<String>> list = getList();

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, contentDispositionHeader("output.xlsx"))
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(inputStreamResource(list));
  }

  private InputStreamResource inputStreamResource(List<List<String>> list) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    ExcelWriter.write(list, byteArrayOutputStream);

    return new InputStreamResource(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
  }

  private String contentDispositionHeader(String fileName) {
    return "attachment;filename=\"" + fileName + "\"";
  }


  private List<List<String>> getList() {
    List<List<String>> list = new ArrayList<>();
    list.add(Arrays.asList("id", "username"));
    list.add(Arrays.asList("id01", "username01"));
    list.add(Arrays.asList("id02", "username02"));
    list.add(Arrays.asList("id03", "username03"));
    return list;
  }
}
