package com.podolab.api.reservation;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HoldRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long seatId;
}