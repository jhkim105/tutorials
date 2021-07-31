package com.example.demo;

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
@EnableDynamoDBRepositories(basePackages = "com.example.demo.repository")
public class DynamoDBConfig {

  @Value("${amazon.dynamodb.endpoint}")
  private String amazonDynamoDBEndpoint;

  @Value("${amazon.dynamodb.region}")
  private String amazonDynamoDBRegion;

  @Value("${amazon.aws.accesskey}")
  private String amazonAWSAccessKey;

  @Value("${amazon.aws.secretkey}")
  private String amazonAWSSecretKey;

  @Bean
  public AmazonDynamoDB amazonDynamoDB() {

    AmazonDynamoDBClientBuilder builder = AmazonDynamoDBClientBuilder.standard()
        .withCredentials(credentialsProvider())
        ;

    if (StringUtils.hasText(amazonDynamoDBEndpoint)) {
      builder.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(amazonDynamoDBEndpoint, amazonDynamoDBRegion));
    } else {
      builder.withRegion(Regions.fromName(amazonDynamoDBRegion));
    }

    return builder.build();

  }

  @Bean
  public AWSStaticCredentialsProvider credentialsProvider() {
    return new AWSStaticCredentialsProvider(new BasicAWSCredentials(
        amazonAWSAccessKey, amazonAWSSecretKey));
  }

}
