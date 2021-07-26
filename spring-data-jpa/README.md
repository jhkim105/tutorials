## 도메인 모델링
* Setter는 꼭 필요한 경우에만 사용
  - 데이터가 어디서 변경되었는지 추적하기 어려워 유지보수하기 힘들다
* 모든 연관관계의 페치 전략은 LAZY로
  - 연관된 데이터를 함께 조회해야 하는 경우 fetch join 또는 EntityGraph 사용
* 컬렉션은 필드에서 초기화
  - null 문제
  - 하아버네이트는 엔터티를 영속화 할 때, 컬렉션을 감싸서 하이버네이트 내장 컬력센으로 변경함. 이 때 문제가 발생할 수 있다.
  
  
  
## References
* https://www.baeldung.com/spring-data-query-by-example
 
