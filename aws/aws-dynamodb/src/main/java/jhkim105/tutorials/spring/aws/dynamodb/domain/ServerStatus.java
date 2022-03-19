package jhkim105.tutorials.spring.aws.dynamodb.domain;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
@DynamoDBTable(tableName = "server")
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
