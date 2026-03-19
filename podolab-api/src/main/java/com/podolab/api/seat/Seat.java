package com.podolab.api.seat;

import com.podolab.api.concert.Concert;
import com.podolab.api.global.entity.BaseEntity;
import com.podolab.api.global.exception.BaseException;
import com.podolab.api.global.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "seat")
public class Seat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id", nullable = false)
    private Concert concert;

    @Column(nullable = false)
    private int seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatStatus status;

    @Builder
    private Seat(Concert concert, int seatNumber, SeatStatus status) {
        this.concert = concert;
        this.seatNumber = seatNumber;
        this.status = status;
    }

	// 좌석 선점: 결제 페이지 진입 시 호출
    public void reserve() {
        if (this.status != SeatStatus.AVAILABLE) {
            throw new BaseException(ErrorCode.SEAT_NOT_AVAILABLE);
        }
        this.status = SeatStatus.HELD;
    }

	// 예약 확정: 결제 완료 시 호출
    public void confirm() {
        if (this.status != SeatStatus.HELD) {
            throw new BaseException(ErrorCode.SEAT_NOT_HELD);
        }
        this.status = SeatStatus.RESERVED;
    }

	// 선점 취소: 결제 페이지 이탈 또는 타임아웃 시 호출
    public void cancelHold() {
        if (this.status != SeatStatus.HELD) {
            throw new BaseException(ErrorCode.SEAT_NOT_HELD);
        }
        this.status = SeatStatus.AVAILABLE;
    }

	// 예약 취소: 결제 완료 후 취소 요청 시 호출
	public void cancelReservation() {
		if (this.status != SeatStatus.RESERVED) {
			throw new BaseException(ErrorCode.SEAT_NOT_RESERVED);
		}
		this.status = SeatStatus.AVAILABLE;
	}
}