package com.podolab.api.concert;

import com.podolab.api.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private LocalDateTime concertDate;

    @Column(nullable = false)
    private int totalSeats;

    @Builder
    private Concert(String title, LocalDateTime concertDate, int totalSeats) {
        this.title = title;
        this.concertDate = concertDate;
        this.totalSeats = totalSeats;
    }
}