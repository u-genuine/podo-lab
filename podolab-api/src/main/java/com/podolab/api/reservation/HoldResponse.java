package com.podolab.api.reservation;

public record HoldResponse(
        Long seatHoldId
) {
    public static HoldResponse of(Long seatHoldId) {
        return new HoldResponse(seatHoldId);
    }
}