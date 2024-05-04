Spring Data Jpa
===================


## Entity
* Setter 제거, Getter 는 필요한 경우에만
* 모든 연관관계의 페치 전략은 LAZY로
  - 연관된 데이터를 함께 조회해야 하는 경우 fetch join 또는 EntityGraph 사용 (n+1 방지)
* 컬렉션은 필드에서 초기화
  - null 문제
  - 하이버네이트는 엔터티를 영속화 할 때, 컬렉션을 감싸서 하이버네이트 내장 컬력센으로 변경함. 이 때 문제가 발생할 수 있다.


## OneToOne

### 주키 공유
User - UserProfile
  ```java
  public class UserProfile {
  
    @Id
    private String id;
  
    @OneToOne
    @MapsId
    private User user;
    
  }
  ```

### 참조 관계
- ManyToOne 과 테이블은 동일하다. 
- User - Membership
  ```java
  public class Membership {
    @Id
    @UuidGenerator
    @Column(length = 50)
    private String id;
  
    @OneToOne
    private User user;
    
  }
  ```
