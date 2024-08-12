# MSA exam

> MSA 구성, Redis 캐싱, ~~Docker 기반 CI/CD 구성~~

# 필수 기능

## 1. 기본 API 구성하기 ✅
- 상품 추가 API `POST /products`
- 상품 목록 조회 API `GET /products`
- 주문 추가 API `POST /order`
- 주문 상품 추가 API `PUT /order/{orderId}`
- 주문 단건 조회 API `GET /order/{orderId}`
- 로그인 API `GET /auth/signIn`
## 2. 상품 서비스는 라운드로빈 형식으로 로드밸런싱 구성하기 ✅
- 상품 목록 조회시 서버 포트가 19093, 19094로 반복하며 호출되게 구성
## 3. 주문에 상품 추가 API 생성시 로직 ✅
1. FeignClient를 이용해 주문 서비스에 상품 서비스 클라이언트 연결
2. 상품 목록 조회 API 호출시 파라미터로 받은 product_id가 상품 목록에 존재하는지 검증
3. 존재할경우 주문에 상품을 추가하고, 존재하지 않는다면 주문에 저장하지 않음
## 4. 분산추적 구현해보기 ✅
- 주문 서비스와 상품 서비스에 Zipkin 연동
## 5. 캐싱 기능 구현하기 ✅
- 주문 조회 API 결과 캐싱 처리
## 6. 외부 요청 보호 ✅
- Oauth2, JWT 기반 인증/인가 구현
## 7. 상품 추가 API 호출 시 상품 목록 조회 API 응답 데이터 캐시가 갱신되도록 구현 ✅
## 8. 로컬과 서버 환경 분리 ✅

