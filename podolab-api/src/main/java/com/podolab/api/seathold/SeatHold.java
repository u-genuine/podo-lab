package com.podolab.api.seathold;

import com.podolab.api.concert.Concert;
import com.podolab.api.global.entity.BaseEntity;
import com.podolab.api.global.exception.BaseException;
import com.podolab.api.global.exception.ErrorCode;
import com.podolab.api.seat.Seat;
import com.podolab.api.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "seat_hold")
public class SeatHold extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatHoldStatus status;

    @Builder(access = AccessLevel.PRIVATE)
    private SeatHold(Seat seat, User user, LocalDateTime expiredAt, SeatHoldStatus status) {
        this.seat = seat;
        this.user = user;
        this.expiredAt = expiredAt;
        this.status = status;
    }

    public static SeatHold create(User user, Seat seat, LocalDateTime expiredAt) {
        return SeatHold.builder()
                .user(user)
                .seat(seat)
                .expiredAt(expiredAt)
                .status(SeatHoldStatus.ACTIVE)
                .build();
    }

    // 점유 해제: 이탈 또는 타임아웃 시 호출
    public void release() {
        if (this.status != SeatHoldStatus.ACTIVE) {
            throw new BaseException(ErrorCode.HOLD_NOT_ACTIVE);
        }
        this.status = SeatHoldStatus.EXPIRED;
        this.seat.cancelHold();
    }

    // 점유 확정: 결제 완료 시 호출
    public void confirm() {
        if (this.status != SeatHoldStatus.ACTIVE) {
            throw new BaseException(ErrorCode.HOLD_NOT_ACTIVE);
        }
        this.status = SeatHoldStatus.CONFIRMED;
        this.seat.confirm();
    }

	public Concert getConcert() {
		return this.seat.getConcert();
	}
}