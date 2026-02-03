---
name: 버그 수정
about: 발견된 버그를 기록하고 해결
title: "[BUG] "
labels: bug
assignees: ''
---

## 🐛 버그 내용
> 어떤 문제가 발생했나요?

<!-- 예시: seatId가 null일 때 500 에러 발생 -->


## 🔍 재현 방법
1.
2.
3.

<!-- 
예시:
1. Postman에서 POST /api/reservations 요청
2. Request Body에서 seatId 필드 제거
3. 500 Internal Server Error 발생
-->


## 📋 예상 결과
> 원래 어떻게 동작해야 하나요?

<!-- 예시: 400 Bad Request와 "seatId는 필수입니다" 메시지 반환 -->


## 💥 실제 결과
> 실제로는 어떻게 동작하나요?

<!-- 예시: 500 에러 발생, 서버 로그에 NullPointerException -->


## 💡 예상 원인

<!-- 
예시:
- Request DTO에 validation 어노테이션 누락
- Controller에서 null 체크 없이 바로 Service 호출
-->


## ✅ 체크리스트
- [ ] 
- [ ] 

<!-- 
예시:
- [ ] Request DTO에 @NotNull 추가
- [ ] 에러 메시지 명확하게 수정
- [ ] Postman으로 수정 확인
-->


## 📸 스크린샷 / 에러 로그 (선택사항)
<!-- 에러 발생 화면, 로그 등 -->