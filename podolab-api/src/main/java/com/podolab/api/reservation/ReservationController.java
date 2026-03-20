package com.podolab.api.reservation;

import com.podolab.api.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/hold")
    public ResponseEntity<ApiResponse<Long>> hold(@RequestBody @Valid HoldRequest request) {
        Long seatHoldId = reservationService.hold(request.getUserId(), request.getSeatId());
        return ResponseEntity.ok(ApiResponse.ok(seatHoldId));
    }

    @PostMapping("/release")
    public ResponseEntity<ApiResponse<Void>> release(@RequestBody @Valid ReleaseRequest request) {
        reservationService.release(request.getSeatHoldId());
        return ResponseEntity.ok(ApiResponse.ok(null));
    }

    @PostMapping("/confirm")
    public ResponseEntity<ApiResponse<Void>> confirm(@RequestBody @Valid ConfirmRequest request) {
        reservationService.confirm(request.getSeatHoldId(), request.getUserId());
        return ResponseEntity.ok(ApiResponse.ok(null));
    }
}