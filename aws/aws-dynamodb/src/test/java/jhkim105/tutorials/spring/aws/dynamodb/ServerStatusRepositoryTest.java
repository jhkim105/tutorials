package jhkim105.tutorials.spring.aws.dynamodb;


import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import java.util.ArrayList;
import java.util.List;
import jhkim105.tutorials.spring.aws.dynamodb.domain.ServerStatus;
import jhkim105.tutorials.spring.aws.dynamodb.repository.ServerStatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ServerStatusRepositoryTest {

  @Autowired
  private AmazonDynamoDB amazonDynamoDB;

  private DynamoDBMapper dynamoDBMapper;

  @Autowired
  private ServerStatusRepository repository;

  @BeforeAll
  void setup() throws Exception {
    try {
      dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
      CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(ServerStatus.class);
      tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
      amazonDynamoDB.createTable(tableRequest);

    } catch (ResourceInUseException  e) {
      log.info(e.getMessage());
      // do nothing: already created
    }

    try {
      dynamoDBMapper.save(ServerStatus.builder().id("id01").status("status01").build());
      dynamoDBMapper.save(ServerStatus.builder().id("id02").status("status02").build());
    } catch(AmazonDynamoDBException e) {
      log.info(e.getMessage());
      // do nothing: already exists
    }

  }

  @Test
  void findAll() {
    Iterable<ServerStatus> serverStatusIterable = repository.findAll();
    List<ServerStatus> serverStatusList = new ArrayList<>();
    serverStatusIterable.forEach(serverStatusList::add);
    log.info("{}", serverStatusList);
  }

  @Test
  void save() {
    ServerStatus serverStatus = ServerStatus.builder().id("id03").status("status03").build();
    repository.save(serverStatus);

    ServerStatus serverStatus2 = repository.findById("id03").orElseThrow();
    log.info("{}", serverStatus2);
  }

  @AfterAll
  void deleteAll() {
    dynamoDBMapper.batchDelete((List<ServerStatus>) repository.findAll());
  }

}
