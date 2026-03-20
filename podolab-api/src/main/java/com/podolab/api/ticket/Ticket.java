package com.podolab.api.ticket;

import com.podolab.api.concert.Concert;
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
@Table(name = "ticket")
public class Ticket extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id", nullable = false)
    private Concert concert;

    @Column(nullable = false)
    private LocalDateTime issuedAt;

    @Builder(access = AccessLevel.PRIVATE)
    private Ticket(User user, Seat seat, Concert concert, LocalDateTime issuedAt) {
        this.user = user;
        this.seat = seat;
        this.concert = concert;
        this.issuedAt = issuedAt;
    }

    public static Ticket create(User user, Seat seat, Concert concert) {
        return Ticket.builder()
                .user(user)
                .seat(seat)
                .concert(concert)
                .issuedAt(LocalDateTime.now())
                .build();
    }
}