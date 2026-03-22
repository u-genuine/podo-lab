package com.podolab.api.reservation;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.podolab.api.concert.Concert;
import com.podolab.api.concert.ConcertRepository;
import com.podolab.api.seat.Seat;
import com.podolab.api.seat.SeatRepository;
import com.podolab.api.seat.SeatStatus;
import com.podolab.api.seathold.SeatHoldRepository;
import com.podolab.api.user.User;
import com.podolab.api.user.UserRepository;

@SpringBootTest
class ReservationServiceTest {

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private ConcertRepository concertRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SeatRepository seatRepository;

	@Autowired
	private SeatHoldRepository seatHoldRepository;

	private static final int threadCount = 10;

	private Long seatId;
	private List<Long> userIds;

	@BeforeEach
	void setUp() {
		Concert concert = concertRepository.save(
			Concert.create("테스트 콘서트", LocalDate.now().plusDays(7), 100)
		);

		Seat seat = seatRepository.save(
			Seat.create(concert, 1, SeatStatus.AVAILABLE)
		);
		seatId = seat.getId();

		userIds = new ArrayList<>();
		for (int i = 0; i < threadCount; i++) {
			User user = userRepository.save(User.create("유저" + (i + 1), "user" + (i + 1) + "@test.com"));
			userIds.add(user.getId());
		}
	}

	@AfterEach
	void tearDown() {
		seatHoldRepository.deleteAll();
		seatRepository.deleteAll();
		concertRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Test
	void 동시에_여러명이_같은_좌석_점유_시도() throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(threadCount);
		CountDownLatch startLatch = new CountDownLatch(1); // 두 스레드 동시 출발시키기 위한 카운터
		CountDownLatch doneLatch = new CountDownLatch(threadCount); // 두 스레드가 모두 종료될 때까지 메인 스레드 대기하기 위한 카운터
		AtomicInteger successCount = new AtomicInteger(0); // 멀티스레드 환경에서 안전하게 성공 횟수 카운트

		for (int i = 0; i < threadCount; i++) {
			long userId = userIds.get(i);
			executor.execute(() -> {
				try {
					startLatch.await(); // startLatch가 0이 될 때까지 대기 (두 스레드 동시 출발하도록)
					reservationService.hold(userId, seatId);
					successCount.incrementAndGet(); // hold() 성공 시 카운트
				} catch (Exception e) {
					// hold 실패 — 정상적으로 거절된 케이스
				} finally {
					doneLatch.countDown(); // 스레드 종료 시 doneLatch 카운트 감소
				}
			});
		}

		startLatch.countDown(); // 카운트를 0으로 만들어 대기 중인 두 스레드 동시 출발
		boolean completed = doneLatch.await(5, TimeUnit.SECONDS); // 두 스레드가 모두 종료될 때까지(doneLatch가 0이 될 때까지) 최대 5초 대기
		executor.shutdown();
		assertThat(completed).isTrue(); // 5초 안에 두 스레드가 정상 종료됐는지 확인

		// 락이 없는 상태에서 여러 스레드가 동시에 접근하면 Race Condition이 발생한다.
		// successCount > 1이면 동시성 문제가 재현된 것이므로 테스트 실패가 정상이다.
		assertThat(successCount.get()).isEqualTo(1);
	}
}