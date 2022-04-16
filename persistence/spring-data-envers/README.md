Envers Example
=======================


## Envers 설정

Dependency
```
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-envers</artifactId>
    <version>5.6.4.Final</version>
</dependency>
```

클래스 또는 필드에 @Audited 추가, 연관 객체가 @Audit 아닐 경우  @NotAudited를 추가

```java
@Entity
@Audited
public class User {

  @OneToMany(mappedBy = "user")
  private Set<UserAddress> userAddresses = new HashSet<>();
  
  @OneToMany(mappedBy = "user")
  @NotAudited
  private Set<Order> orders = new HashSet<>();
  
  
}
```

```java
@Entity
@Audited
public class UserAddress  {
```

```java
@Entity
public class Order  {
```

### REV를 long 으로 변경하기
기본은 int 이다. 이것을 long 으로 변경하려면 @evisionEntity class를 생성하고, @RevisionNumber 를 지정해주면 된다.

Revision.java
```java
@Entity
@Table(name = "dz_revision")
@RevisionEntity
@Getter
@EqualsAndHashCode(of = "id")
public class Revision {

  @Id
  @GeneratedValue
  @RevisionNumber
  private long id;

  @RevisionTimestamp
  private long timestamp;

}
```

## Entity History 조회하기
단건조회
```java
  public Object getRevisionObject(Class<?> clazz, long rev) {
    AuditQuery query = AuditReaderFactory.get(entityManager)
        .createQuery()
        .forEntitiesAtRevision(clazz, rev);
    query.setMaxResults(1);
    List list = query.getResultList();
    return CollectionUtils.isEmpty(list) ? null : list.get(0);
  }

```
다건 조회
```java
  public List getRevisionObjects(Class<?> clazz, int maxResults) {
    AuditQuery query = AuditReaderFactory.get(entityManager)
        .createQuery()
        .forRevisionsOfEntity(clazz, true, true);
    query.addOrder(AuditEntity.revisionProperty("timestamp").desc());
    query.setMaxResults(maxResults);

    return query.getResultList();
  }

```

실행 쿼리를 보면 cross join 이 발생한다. 조건절에 user_aud0_.rev=revision1_.id 이 있어서 성능상 이슈는 없다.(mysql 에서는 조건이 있는 경우 inner join 과 동일하게 동작)
```shell
select user_aud0_.id as id1_6_, user_aud0_.rev as rev2_6_, user_aud0_.revtype as revtype3_6_, user_aud0_.name as name4_6_, user_aud0_.username as username5_6_ 
from dm_user_aud user_aud0_ 
cross join dz_revision revision1_ 
where user_aud0_.rev=revision1_.id 
order by revision1_.timestamp desc limit ?
```