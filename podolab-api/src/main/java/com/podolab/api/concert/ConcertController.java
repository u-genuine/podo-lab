package com.podolab.api.concert;

import com.podolab.api.global.response.ApiResponse;
import com.podolab.api.seat.SeatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/concerts")
public class ConcertController {

    private final ConcertService concertService;

    @GetMapping("/{concertId}/seats")
    public ResponseEntity<ApiResponse<List<SeatResponse>>> getSeats(@PathVariable Long concertId) {
        return ResponseEntity.ok(ApiResponse.ok(concertService.getSeats(concertId)));
    }
}