package com.podolab.api.reservation;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReleaseRequest {

    @NotNull
    private Long seatHoldId;
}