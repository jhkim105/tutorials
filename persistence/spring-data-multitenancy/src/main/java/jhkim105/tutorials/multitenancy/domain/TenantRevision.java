package jhkim105.tutorials.multitenancy.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

@Entity
@Table(name = "tenant_revision")
@RevisionEntity
@Getter
@EqualsAndHashCode(of = "id")
public class TenantRevision {

  @Id
  @GeneratedValue
  @RevisionNumber
  private long id;

  @RevisionTimestamp
  private long timestamp;
}
