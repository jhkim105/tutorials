package jhkim105.tutorials.aws.s3.config;


import java.net.URI;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AwsS3Properties {
  private String profileName;
  private String region;
  private URI endpoint;
  private boolean localstack;

  
}
