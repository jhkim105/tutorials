package jhkim105.tutorials.aws.s3.config;


import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsS3Config {

  @Bean
  @ConfigurationProperties(prefix = "aws.s3")
  public AwsS3Properties s3Properties() {
    return new AwsS3Properties();
  }

  @Bean
  public AmazonS3 s3Client(AwsS3Properties s3Properties) {
    AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard()
        .withCredentials(profileCredentialsProvider(s3Properties.getProfileName()));

    if (s3Properties.isLocalstack()) {
      EndpointConfiguration endpointConfiguration =
          new EndpointConfiguration(s3Properties.getEndpoint().toString(), Regions.US_EAST_1.getName());
      builder.withEndpointConfiguration(endpointConfiguration);
      builder.withPathStyleAccessEnabled(true);
    } else {
      builder.withRegion(s3Properties.getRegion());
    }

    return builder.build();
  }

  private AWSCredentialsProvider profileCredentialsProvider(String profileName) {
    return new ProfileCredentialsProvider(profileName);
  }
//  @Bean
//  @ConditionalOnProperty(name = "aws.localstack.enabled", havingValue = "true")
//  public AmazonS3 localstackClient(@Value("${aws.localstack.endpoint}") String localstackEndpoint) {
//    EndpointConfiguration endpointConfiguration =
//        new EndpointConfiguration(localstackEndpoint, Regions.US_EAST_1.name());
//    return AmazonS3ClientBuilder.standard()
//        .withEndpointConfiguration(endpointConfiguration)
//        .withCredentials(staticCredentialsProvider("any", "any"))
//        .withPathStyleAccessEnabled(true)
//        .build();
//  }
//
//  private AWSCredentialsProvider staticCredentialsProvider(String accessKey, String secretKey) {
//    return new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey));
//  }

  @Bean
  public TransferManager transferManager(AmazonS3 s3Client) {
    return TransferManagerBuilder.standard()
        .withS3Client(s3Client)
        .build();
  }



}
