package com.podolab.api.concert;

import com.podolab.api.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "concert")
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

    @Builder(access = AccessLevel.PRIVATE)
    private Concert(String title, LocalDate concertDate, int totalSeats) {
        this.title = title;
        this.concertDate = concertDate;
        this.totalSeats = totalSeats;
    }

    public static Concert create(String title, LocalDate concertDate, int totalSeats) {
        return Concert.builder()
                .title(title)
                .concertDate(concertDate)
                .totalSeats(totalSeats)
                .build();
    }
}