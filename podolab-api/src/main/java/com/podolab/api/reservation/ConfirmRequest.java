package com.podolab.api.reservation;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConfirmRequest {

    @NotNull
    private Long seatHoldId;

    @NotNull
    private Long userId;
}