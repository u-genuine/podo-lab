package com.podolab.api.reservation;

import jakarta.validation.constraints.NotNull;

public record ReleaseRequest(
        @NotNull Long concertId,
        @NotNull Integer seatNumber,
        @NotNull Long userId
) {
}