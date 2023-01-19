Spring Security JWT
=============================

## 인증 Flow
- (Client) username/password 로  로그인
- (Server) JWT 토큰 발행
- (Client) JWT 토큰 포함하여 요청
- (Server) JWT 토큰 검증
    - 유효하지 않은 경우 Error Response

## 구현해야 할 것들
- Filter: 인증처리
- Handler: 성공, 예외 메시지 응답 처리
- Provider

## 구현하기
### JwtAuthenticationFilter
AbstractAuthenticationProcessingFilter 를 상속, Token 인증시 DB 조회 안함

### JwtAuthenticationToken
UsernamePasswordAuthenticationToken 를 참고하여 구현

### JwtAuthenticationProvider
JwtAuthenticationToken 을 support 하는 Provider 
인증 정보를 제공하여 인증을 처리함. 여기서는 DB 조회를 하지 않으므로 인증통과용 생성자를 호출하여 Authentication 객체를 생성한다.

