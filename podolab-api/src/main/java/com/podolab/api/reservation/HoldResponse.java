package com.podolab.api.reservation;

public record HoldResponse(
        Long concertId,
        int seatNumber
) {
    public static HoldResponse of(Long concertId, int seatNumber) {
        return new HoldResponse(concertId, seatNumber);
    }
}