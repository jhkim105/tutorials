package jhkim105.tutorials.multitenancy.master.domain;

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
@Table(name = "master_revision")
@RevisionEntity
@Getter
@EqualsAndHashCode(of = "id")
public class MasterRevision {
  @Id
  @GeneratedValue
  @RevisionNumber
  private long id;

  @RevisionTimestamp
  private long timestamp;
}
