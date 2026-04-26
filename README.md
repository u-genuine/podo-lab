# 🍇 PodoLab

티켓 오픈 시 발생하는 트래픽 폭주와 동시성 문제를 단계적으로 해결해 나가는 티켓팅 시뮬레이션 프로젝트

<br>
  
## 배경
PodoLab은 실제 티켓팅에서 발생하는 문제들을 재현하고 해결 과정을 기록합니다.

**주요 문제:**
- 좌석을 여러 사용자가 동시에 선택했을 때 중복 예약 문제
- 이미 점유된 좌석도 DB까지 도달하는 불필요한 조회
- 다수의 사용자가 동시에 좌석을 조회할 때 DB 부하
- 대규모 트래픽으로 인한 서버 마비
- (추가 예정)

<br>

## 진행 상황
- [x] Step 1: 기본 티켓팅 API + CI/CD 구성
  - 티켓팅 시뮬레이션 도메인 설계 (Concert, Seat, Ticket, User)
  - GitHub Actions CI/CD 파이프라인 구성
  - PR 테스트 자동화 (CI/CD 분리)

- [x] Step 2: 동시성 문제 재현 & 해결
  - 동시 좌석 점유 문제 재현 및 락 적용 ([락 전략 선택 과정](https://u-genuine.tistory.com/43))
  - Redis 도입으로 중복 점유 요청의 불필요한 DB 조회 개선 ([PR](https://github.com/u-genuine/podo-lab/pull/23))
  
- [x] Step 3: 조회 성능 개선
  - 좌석 조회 Redis 캐싱 도입 및 부하테스트 ([PR](https://github.com/u-genuine/podo-lab/pull/31))
  - 티켓팅 오픈 전 좌석 캐시 워밍 스케줄러 구현 ([PR](https://github.com/u-genuine/podo-lab/pull/34))

- [ ] Step 4: 대규모 트래픽 대응
- [ ] Step 5: 좌성 상태 실시간 처리

(업데이트 예정)

<br>

## 기술 스택
**Backend**: Java 17, Spring Boot 3.x, Spring Data JPA, MySQL, Redis
**Infra**: AWS (EC2, RDS, ElastiCache, ECR, CodeDeploy), Docker, GitHub Actionㄴ

<br>

## 아키텍처

```
[VPC 내부]
EC2 (Docker > Spring Boot API)
├──→ ElastiCache (Redis)
└──→ RDS (MySQL)

[배포 파이프라인]
GitHub Actions → ECR → CodeDeploy → EC2
```
