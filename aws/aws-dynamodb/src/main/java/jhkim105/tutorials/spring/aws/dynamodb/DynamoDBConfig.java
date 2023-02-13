package jhkim105.tutorials.spring.aws.dynamodb;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class DynamoDBConfig {

  @Value("${aws.profileName}")
  private String profileName;


  @Value("${aws.dynamodb.endpoint}")
  private String awsDynamoDBEndpoint;

  @Value("${aws.dynamodb.region}")
  private String awsDynamoDBRegion;


  @Bean
  public DynamoDBMapper dynamoDBMapper(AmazonDynamoDB amazonDynamoDB) {
    return new DynamoDBMapper(amazonDynamoDB);
  }

  @Bean
  public AmazonDynamoDB amazonDynamoDB() {

    AmazonDynamoDBClientBuilder builder = AmazonDynamoDBClientBuilder.standard()
        .withCredentials(credentialsProvider())
        ;

    if (StringUtils.hasText(awsDynamoDBEndpoint)) {
      builder.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(awsDynamoDBEndpoint, awsDynamoDBRegion));
    } else {
      builder.withRegion(Regions.fromName(awsDynamoDBRegion));
    }

    return builder.build();

  }

  @Bean
  public AWSCredentialsProvider credentialsProvider() {
    return new ProfileCredentialsProvider(profileName);
  }

}
