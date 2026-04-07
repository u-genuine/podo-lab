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
    public ResponseEntity<ApiResponse<HoldResponse>> hold(@RequestBody @Valid HoldRequest request) {
        Long seatId = reservationService.hold(request.userId(), request.seatId());
        return ResponseEntity.ok(ApiResponse.ok(HoldResponse.of(seatId)));
    }

    @PostMapping("/release")
    public ResponseEntity<ApiResponse<Void>> release(@RequestBody @Valid ReleaseRequest request) {
        reservationService.release(request.seatId(), request.userId());
        return ResponseEntity.ok(ApiResponse.ok(null));
    }

    @PostMapping("/confirm")
    public ResponseEntity<ApiResponse<Void>> confirm(@RequestBody @Valid ConfirmRequest request) {
        reservationService.confirm(request.seatId(), request.userId());
        return ResponseEntity.ok(ApiResponse.ok(null));
    }
}