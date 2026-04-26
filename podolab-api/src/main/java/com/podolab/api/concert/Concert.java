package com.podolab.api.concert;

import com.podolab.api.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "concert", indexes = {
        @Index(name = "idx_concert_open_at", columnList = "open_at")
})
public class Concert extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate concertDate;

    @Column(nullable = false)
    private int totalSeats;

    @Column(nullable = false)
    private LocalDateTime openAt;

    @Builder(access = AccessLevel.PRIVATE)
    private Concert(String title, LocalDate concertDate, int totalSeats, LocalDateTime openAt) {
        this.title = title;
        this.concertDate = concertDate;
        this.totalSeats = totalSeats;
        this.openAt = openAt;
    }

    public static Concert create(String title, LocalDate concertDate, int totalSeats, LocalDateTime openAt) {
        return Concert.builder()
                .title(title)
                .concertDate(concertDate)
                .totalSeats(totalSeats)
                .openAt(openAt)
                .build();
    }
}