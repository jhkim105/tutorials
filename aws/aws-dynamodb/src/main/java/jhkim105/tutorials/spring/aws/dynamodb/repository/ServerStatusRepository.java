package jhkim105.tutorials.spring.aws.dynamodb.repository;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import java.util.Optional;
import jhkim105.tutorials.spring.aws.dynamodb.domain.ServerStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ServerStatusRepository  {
  private final DynamoDBMapper dynamoDBMapper;


  public Iterable<ServerStatus> findAll() {
    return dynamoDBMapper.scan(ServerStatus.class, new DynamoDBScanExpression());
  }

  public void save(ServerStatus serverStatus) {
    dynamoDBMapper.save(serverStatus);
  }

  public Optional<ServerStatus> findById(String id) {
    return Optional.ofNullable(dynamoDBMapper.load(ServerStatus.class, id));
  }
}
