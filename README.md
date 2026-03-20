# 🍇 PodoLab

티켓 오픈 시 발생하는 트래픽 폭주와 동시성 문제를 단계적으로 해결해 나가는 티켓팅 시뮬레이션 프로젝트

  
## 배경
PodoLab은 실제 티켓팅에서 발생하는 문제들을 재현하고 해결 과정을 기록합니다.

**주요 문제:**
- 좌석을 여러 사용자가 동시에 선택했을 때 중복 예약 문제
- 대규모 트래픽으로 인한 서버 마비
- 이미 선택된 좌석 정보를 매번 DB에서 조회하는 비효율 문제
- (추가 예정)


## 진행 상황
- [ ] Step 1: 기본 티켓팅 API
- [ ] Step 2: 동시성 문제 재현 & 해결
- [ ] Step 3: 대규모 트래픽 대응

## 기술 스택
**Backend**
- Java 17, Spring Boot 3.x, Spring Data JPA

**Database**
- MySQL

**Infra**
- AWS EC2, AWS RDS
- AWS ECR, AWS CodeDeploy, AWS S3
- Docker
- GitHub Actions (CI/CD)
