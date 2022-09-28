Open Session In View
================================

### OSIV 사용시 성능관련 문제점
- 커넥션 낭비
    - request scope에서 session(Hibernate Session)이 유지되므로,  connection 이 요청이 종료될때 까지 반환하지 않는다.
- 불필요한 쿼리
    - Session 이 전체 request 생명주기 동안 유지되므로 transaction context 밖에서 원하지 않는 부가적인 쿼리가 실행될수 있다.(n+1 select)
    - 추가 실행되는 쿼리는 auto commit mode 에서 실행된다. 각 sql 문이 transaction 으로 처리되며, 실행된 후에 자동으로 commit 된다. 이것은 데이터베이스에 부담을 주고, 의도하지 않은 데이터 변경을 야기할 수 있다
    
### 대안
- Hibernate.initialize(), touch(user.getRoles().size())
  - 추가 쿼리가 실행되는 문제가 있음

- Entity Graphs, Fetch Join
