package jhkim105.tutorials.authorization_server_jpa.repository;

import java.util.Optional;
import jhkim105.tutorials.authorization_server_jpa.domain.AuthorizationConsent;
import jhkim105.tutorials.authorization_server_jpa.domain.AuthorizationConsent.AuthorizationConsentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationConsentRepository extends JpaRepository<AuthorizationConsent, AuthorizationConsentId> {
	Optional<AuthorizationConsent> findByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);
	void deleteByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);
}