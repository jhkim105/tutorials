package jhkim105.tutorials.aws.sdk2.s3.config;


import java.net.URI;
import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.regions.Region;

@Getter
@Setter
public class AwsS3Properties {
  private String profileName;
  private Region region;
  private URI endpoint;

  
}
