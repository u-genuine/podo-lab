package com.podolab.api.seat;

public record SeatResponse(
        int seatNumber,
        boolean available
) {
    public static SeatResponse of(Seat seat) {
        return new SeatResponse(
                seat.getSeatNumber(),
                seat.getStatus() == SeatStatus.AVAILABLE
        );
    }
}