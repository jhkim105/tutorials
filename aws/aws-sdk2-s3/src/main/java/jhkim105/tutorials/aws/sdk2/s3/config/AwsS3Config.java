package jhkim105.tutorials.aws.sdk2.s3.config;


import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.http.async.SdkAsyncHttpClient;
import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3AsyncClientBuilder;
import software.amazon.awssdk.services.s3.S3Configuration;

@Configuration
public class AwsS3Config {

  @Bean
  @ConfigurationProperties(prefix = "aws.s3")
  public AwsS3Properties s3Properties() {
    return new AwsS3Properties();
  }


  @Bean
  public AwsCredentialsProvider awsCredentialsProvider(AwsS3Properties s3Properties) {
    return ProfileCredentialsProvider.create(s3Properties.getProfileName());
  }
  @Bean
  public S3AsyncClient s3client(AwsCredentialsProvider credentialsProvider, AwsS3Properties s3Properties) {
    SdkAsyncHttpClient httpClient = NettyNioAsyncHttpClient.builder()
        .writeTimeout(Duration.ZERO)
        .maxConcurrency(64)
        .build();
    S3Configuration serviceConfiguration = S3Configuration.builder()
        .checksumValidationEnabled(false)
        .chunkedEncodingEnabled(true)
        .build();
    S3AsyncClientBuilder b = S3AsyncClient.builder().httpClient(httpClient)
        .region(s3Properties.getRegion())
        .credentialsProvider(credentialsProvider)
        .serviceConfiguration(serviceConfiguration);

    if (s3Properties.getEndpoint() != null) {
      b = b.endpointOverride(s3Properties.getEndpoint());
    }
    return b.build();
  }


}
