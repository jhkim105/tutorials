package jhkim105.tutorials.cqrs.domain;

import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

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
