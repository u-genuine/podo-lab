package com.podolab.api.seat;

public record SeatResponse(
        Long seatId,
        int seatNumber,
        boolean available
) {
    public static SeatResponse of(Seat seat) {
        return new SeatResponse(
                seat.getId(),
                seat.getSeatNumber(),
                seat.getStatus() == SeatStatus.AVAILABLE
        );
    }
}