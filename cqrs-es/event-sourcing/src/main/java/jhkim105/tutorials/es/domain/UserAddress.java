package jhkim105.tutorials.es.domain;

import java.util.List;
import java.util.Map;
import lombok.Getter;

/**
 * Domain Model for Read
 */
@Getter
public class UserAddress {

  private Map<String, List<Address>> addressesByName;

  public UserAddress(Map<String, List<Address>> addressesByName) {
    this.addressesByName = addressesByName;
  }
}
