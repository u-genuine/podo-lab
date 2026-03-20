package com.podolab.api.reservation;

import jakarta.validation.constraints.NotNull;

public record HoldRequest(
        @NotNull Long userId,
        @NotNull Long seatId
) {
}