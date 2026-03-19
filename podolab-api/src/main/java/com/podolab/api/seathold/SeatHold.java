package com.podolab.api.seathold;

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

    @Builder
    private SeatHold(Seat seat, User user, LocalDateTime expiredAt, SeatHoldStatus status) {
        this.seat = seat;
        this.user = user;
        this.expiredAt = expiredAt;
        this.status = status;
    }

    public void release() {
        if (this.status != SeatHoldStatus.ACTIVE) {
            throw new BaseException(ErrorCode.HOLD_NOT_ACTIVE);
        }
        this.status = SeatHoldStatus.EXPIRED;
    }

    public void confirm() {
        if (this.status != SeatHoldStatus.ACTIVE) {
            throw new BaseException(ErrorCode.HOLD_NOT_ACTIVE);
        }
        this.status = SeatHoldStatus.CONFIRMED;
    }
}