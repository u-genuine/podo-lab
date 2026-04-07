package com.podolab.api.reservation;

public record HoldResponse(
        Long seatId
) {
    public static HoldResponse of(Long seatId) {
        return new HoldResponse(seatId);
    }
}