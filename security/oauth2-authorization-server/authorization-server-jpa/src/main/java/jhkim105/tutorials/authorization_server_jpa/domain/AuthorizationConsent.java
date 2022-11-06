package jhkim105.tutorials.authorization_server_jpa.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "`authorizationConsent`")
@IdClass(AuthorizationConsent.AuthorizationConsentId.class)
@Getter
@Setter
public class AuthorizationConsent {

	@Id
	private String registeredClientId;
	@Id
	private String principalName;
	@Column(length = 1000)
	private String authorities;

	@EqualsAndHashCode(of = {"registeredClientId", "principalName"})
	public static class AuthorizationConsentId implements Serializable {
		private String registeredClientId;
		private String principalName;
	}
}