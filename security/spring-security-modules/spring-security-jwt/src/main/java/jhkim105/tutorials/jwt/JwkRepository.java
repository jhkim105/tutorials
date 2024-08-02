package jhkim105.tutorials.jwt;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwkRepository extends JpaRepository<Jwk, String> {

    Optional<Jwk> findTopByOrderByCreatedAtDesc();
}
