package jhkim105.tutorials.spring.data.envers.service;

import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RevisionService {

  private final EntityManager entityManager;

  public List getList(Class<?> clazz, int maxResults) {
    AuditQuery query = AuditReaderFactory.get(entityManager)
        .createQuery()
        .forRevisionsOfEntity(clazz, true, true);
    query.addOrder(AuditEntity.revisionProperty("timestamp").desc());
    query.setMaxResults(maxResults);

    return query.getResultList();
  }

  public long getLatestRevisionNumber(Class<?> clazz, Object entityId) {
    return ((Number) AuditReaderFactory.get(entityManager).createQuery()
        .forRevisionsOfEntity(clazz, true)
        .addProjection(AuditEntity.revisionNumber().max())
        .add(AuditEntity.id().eq(entityId))
        .getSingleResult()).longValue();
  }

  public Object get(Class<?> clazz, long rev) {
    AuditQuery query = AuditReaderFactory.get(entityManager)
        .createQuery()
        .forEntitiesAtRevision(clazz, rev);
    query.setMaxResults(1);
    List list = query.getResultList();
    return CollectionUtils.isEmpty(list) ? null : list.get(0);
  }

  public  boolean isModified(Class<?> clazz, Object id, String columnName, long rev) {
    AuditQuery query = AuditReaderFactory.get(entityManager).createQuery()
        .forEntitiesAtRevision(clazz, rev)
        .add(AuditEntity.id().eq(id))
        .add(AuditEntity.property(columnName).hasChanged());
    Object res = query.getSingleResult();
    return res != null;
  }
}
