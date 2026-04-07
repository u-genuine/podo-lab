package com.podolab.api.reservation;

import jakarta.validation.constraints.NotNull;

public record ConfirmRequest(
        @NotNull Long seatId,
        @NotNull Long userId
) {
}