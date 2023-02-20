package jhkim105.tutorials.aws.s3;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class UploadResponse {

  private final String url;

  public static UploadResponse of(String url) {
    return new UploadResponse(url);
  }
}
