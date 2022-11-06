package jhkim105.tutorials.authorization_server_jpa.repository;

import java.util.Optional;
import jhkim105.tutorials.authorization_server_jpa.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
	Optional<Client> findByClientId(String clientId);
}