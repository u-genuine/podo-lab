package com.podolab.api.reservation;

import com.podolab.api.global.entity.BaseEntity;
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
@Table(name = "reservation")
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @Column(nullable = false)
    private LocalDateTime reservedAt;

    @Builder
    private Reservation(User user, Seat seat, LocalDateTime reservedAt) {
        this.user = user;
        this.seat = seat;
        this.reservedAt = reservedAt;
    }
}