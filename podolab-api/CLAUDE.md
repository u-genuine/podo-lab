# PodoLab API - Claude 컨텍스트

## 프로젝트 개요
티켓팅 시뮬레이션 백엔드. 좌석 예약 동시성 처리 실험이 핵심 목적.

## 기술 스택
- Java 17
- Spring Boot 3.x
- Spring Data JPA
- MySQL
- Lombok
- Gradle

## 패키지 구조
```
com.podolab.api.
├── concert/       # Concert.java, ConcertRepository.java
├── seat/          # Seat.java, SeatStatus.java, SeatRepository.java
├── reservation/   # 핵심 도메인. Controller/Service/Repository/DTO 모두 여기
├── user/          # User.java, UserRepository.java
└── global/
    ├── exception/ # BaseException, ErrorCode, GlobalExceptionHandler
    └── response/  # ApiResponse (공통 응답 포맷)
```
Controller는 reservation만 존재. concert/seat/user는 엔티티와 Repository만.

도메인 객체에 `@Entity`, `@Column` 등 JPA 매핑 어노테이션은 허용한다.
단, 도메인 로직 안에서 Repository를 직접 호출하거나 EntityManager를 사용하지 않는다.

## 코딩 규칙

### 엔티티
- `@Setter` 사용 금지
- 객체 생성은 `@Builder` 사용
- 기본 생성자는 `@NoArgsConstructor(access = AccessLevel.PROTECTED)`
- 비즈니스 로직(상태 변경 등)은 엔티티 내부 메서드로 구현
- JPA Auditing: `createdAt`, `updatedAt` (`@EntityListeners(AuditingEntityListener.class)`)

### Lombok 허용
- `@Getter`
- `@Builder`
- `@NoArgsConstructor(access = AccessLevel.PROTECTED)`
- `@RequiredArgsConstructor`

`@Setter`, `@Data`, `@AllArgsConstructor` 사용 금지.

### 서비스
- 비즈니스 로직은 Service가 아니라 도메인 엔티티 안에
- Service는 흐름 조율만 (Repository 호출 → 도메인 메서드 호출 → 저장)

### 예외처리
- 커스텀 예외는 `BaseException` 상속
- 에러 코드는 `ErrorCode` enum으로 관리
- `GlobalExceptionHandler`에서 일괄 처리

### DTO
- Request: `XxxRequest`
- Response: `XxxResponse`
- 엔티티를 Controller 밖으로 노출하지 말 것

**공통 응답**
모든 API 응답은 `ApiResponse<T>` 포맷 사용.

## 절대 하지 말 것
- `@Setter` 사용
- 엔티티를 Response로 직접 반환
- 요청 없이 테스트 코드 자동 생성
- 요청 없이 `build.gradle` 의존성 수정 (필요한 경우 목록만 알려줄 것)
- 기존 파일 무단 삭제 또는 전면 재작성