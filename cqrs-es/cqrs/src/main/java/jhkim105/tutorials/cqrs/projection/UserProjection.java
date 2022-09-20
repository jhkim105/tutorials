package jhkim105.tutorials.cqrs.projection;

import static java.util.stream.Collectors.groupingBy;

import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityNotFoundException;
import jhkim105.tutorials.cqrs.domain.Address;
import jhkim105.tutorials.cqrs.domain.User;
import jhkim105.tutorials.cqrs.domain.UserAddress;
import jhkim105.tutorials.cqrs.query.AddressByNameQuery;
import jhkim105.tutorials.cqrs.repository.UserReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProjection {

  private final UserReadRepository readRepository;


  public UserAddress handle(AddressByNameQuery query) {
    User user = readRepository.findById(query.getUserId()).orElseThrow(EntityNotFoundException::new);
    Set<Address> addresses = user.getAddresses();
    if(addresses == null || addresses.isEmpty()) {
      return null;
    }
    Map<String, List<Address>> addressMap = addresses.stream().collect(groupingBy(Address::getName));
    return new UserAddress(addressMap);
  }



}
