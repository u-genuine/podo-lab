package com.podolab.api.reservation;

import jakarta.validation.constraints.NotNull;

public record ReleaseRequest(
        @NotNull Long seatHoldId
) {
}