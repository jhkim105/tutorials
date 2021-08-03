package com.example.demo;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
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

  @Value("${aws.accesskey}")
  private String awsAccessKey;

  @Value("${aws.secretkey}")
  private String awsSecretKey;

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
  public AWSStaticCredentialsProvider credentialsProvider() {
    return new AWSStaticCredentialsProvider(new BasicAWSCredentials(
        awsAccessKey, awsSecretKey));
  }

}
