package com.example.spring.data.dynamodb.domain;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@DynamoDBTable(tableName = "server")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ServerStatus {

  @DynamoDBHashKey
  private String id;

  @DynamoDBAttribute
  private String status;

  @Builder
  public ServerStatus(String id, String status) {
    this.id = id;
    this.status = status;
  }
}
