package com.podolab.api.concert;

import com.podolab.api.global.exception.BaseException;
import com.podolab.api.global.exception.ErrorCode;
import com.podolab.api.seat.SeatRepository;
import com.podolab.api.seat.SeatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcertService {

    private final ConcertRepository concertRepository;
    private final SeatRepository seatRepository;

    @Transactional(readOnly = true)
    public List<SeatResponse> getSeats(Long concertId) {
        if (!concertRepository.existsById(concertId)) {
            throw new BaseException(ErrorCode.CONCERT_NOT_FOUND);
        }
        return seatRepository.findByConcertId(concertId).stream()
                .map(SeatResponse::of)
                .toList();
    }
}