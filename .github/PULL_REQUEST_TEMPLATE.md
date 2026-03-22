<!-- PR 제목 예시: [FEAT] 좌석 예약 API 구현 -->

## 🎯 작업 내용
> 한 줄 요약

<!-- 예시: 좌석 예약 기본 API 구현 -->

<br>

## 📝 주요 변경사항

-
-

<!-- 
예시:
- ReservationService 예약 로직 구현
- Seat, Reservation 엔티티 및 Repository 추가
- POST /api/reservations 엔드포인트 추가
-->

<br>

## 💭 구현 과정 / 트러블슈팅
> 구현하면서 겪은 문제, 시도했던 접근, 해결 방식을 자세히 적어두세요  

<!-- 
예시:
- 동시에 같은 좌석을 선택할 때 둘 다 예약되는 문제 발생
- 해결: DB Lock + @Transactional 적용으로 동시성 제어
-->


<br>

## 🧠 배운 점 / 개선 아이디어
> 이번 작업을 통해 배운 점, 다음에 개선하고 싶은 점

<!-- 
예시:
- @Transactional의 동작 방식과 격리 수준에 대해 학습
- 추후 Kafka를 도입해 비동기 처리 개선 예정
-->

<br>

## 🧪 테스트 결과
> 기능 검증 내용과 실행 결과 캡처 또는 로그 첨부

<!-- 
- POST /api/reservations (정상 예약) → 200 OK
- POST /api/reservations (중복 예약) → 400 Bad Request
-->

<br>

## 📸 스크린샷 / 로그 (선택사항)
<!-- Postman 캡처, API 응답 로그 등 -->

<br>

## 🔗 관련 이슈
- Close #


<br>

## 📚 참고 자료 (선택사항)
<!-- 이번 작업에서 참고한 자료 -->