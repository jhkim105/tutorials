

## Java 시간 관련 타입

### DB 에 저장
Java 에서 날짜 관련 타입은 Date 과 Java8 에서 추가된 LocalDateTime, ZonedDateTime, OffsetDateTime 이 있다. 이 3가지는 nanoseconds 까지 저장한다.
MariaDB 는 모두 datetime 타입으로 생성된다.  
Date, LocalDateTime 은 서버 시간 그대로 저장되고 ZonedDateTime, OffsetDateTime 은 UTC 로 변환되어 저장한다.  

### 조회하기
| 타입             | 데이터                                          |
|----------------|----------------------------------------------|
| Date           | Tue Feb 06 22:46:09 KST 2024                 |
| LocalDateTime  | 2024-02-06T22:46:09.108180                   |
| ZonedDateTime  | 2024-02-06T22:46:09.108195+09:00[Asia/Seoul] |
| OffsetDateTime | 2024-02-06T22:46:09.108199+09:00             |



