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


