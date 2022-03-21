package jhkim105.tutorials.spring.aws.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("rm")
@Slf4j
class RemoteMeetingTests {


  @Autowired
  DynamoDBMapper dynamoDBMapper;

  @Autowired
  AmazonDynamoDB client;



  @Test
  void get() {
    Mailing mailing = dynamoDBMapper.load(Mailing.class, "jihwan.kim@rsupport.com");
    log.info("{}", mailing);
  }


  @Test
  void getList() {
    ScanRequest scanRequest = new ScanRequest("mailing");
    scanRequest.setLimit(100);
    ScanResult result = client.scan(scanRequest);
    List<Map<String, AttributeValue>> list = result.getItems();
    log.info("{}", list);
  }

  @DynamoDBTable(tableName="mailing")
  @Getter
  @Setter
  public static class Mailing {

    @DynamoDBHashKey
    private String email;

    @DynamoDBAttribute
    private String notificationType;

    @DynamoDBAttribute
    private String from;

    @DynamoDBAttribute
    private Date timestamp;

    @DynamoDBAttribute
    private String status;

    @DynamoDBAttribute
    private String messageId;

    @DynamoDBAttribute
    private String bounceType;

    @DynamoDBAttribute
    private String bounceSummary;

    @DynamoDBAttribute
    private String complaintType;
  }

}
