package jhkim105.tutorials.spring.data.dynamodb;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
@EnableDynamoDBRepositories(basePackages = "jhkim105.tutorials.spring.data.dynamodb.repository")
public class DynamoDBConfig {

  @Value("${aws.dynamodb.endpoint}")
  private String dynamoDBEndpoint;

  @Value("${aws.dynamodb.region}")
  private String dynamoDBRegion;

  @Value("${aws.accessKey}")
  private String accessKey;

  @Value("${aws.secretKey}")
  private String secretKey;

  @Bean
  public AmazonDynamoDB amazonDynamoDB() {

    AmazonDynamoDBClientBuilder builder = AmazonDynamoDBClientBuilder.standard()
        .withCredentials(credentialsProvider())
        ;

    if (StringUtils.hasText(dynamoDBEndpoint)) {
      builder.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(dynamoDBEndpoint, dynamoDBRegion));
    } else {
      builder.withRegion(Regions.fromName(dynamoDBRegion));
    }

    return builder.build();

  }

  @Bean
  public AWSStaticCredentialsProvider credentialsProvider() {
    return new AWSStaticCredentialsProvider(new BasicAWSCredentials(
        accessKey, secretKey));
  }

}
