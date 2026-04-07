package com.podolab.api.reservation;

import com.podolab.api.global.exception.BaseException;
import com.podolab.api.global.exception.ErrorCode;
import com.podolab.api.seat.Seat;
import com.podolab.api.seat.SeatRepository;
import com.podolab.api.seathold.SeatHold;
import com.podolab.api.seathold.SeatHoldRepository;
import com.podolab.api.ticket.Ticket;
import com.podolab.api.ticket.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private static final String SEAT_HOLD_KEY_PREFIX = "seats:%d:hold";
    private static final Duration SEAT_HOLD_TTL = Duration.ofMinutes(5);

    private final SeatRepository seatRepository;
    private final SeatHoldRepository seatHoldRepository;
    private final TicketRepository ticketRepository;
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
    public Long hold(Long userId, Long seatId) {
        String redisKey = SEAT_HOLD_KEY_PREFIX.formatted(seatId);

		// 키가 없으면 저장 후 true 반환 / 키가 있으면 false 반환
		Boolean acquired = redisTemplate.opsForValue()
			.setIfAbsent(redisKey, String.valueOf(userId), SEAT_HOLD_TTL);

        if (Boolean.FALSE.equals(acquired)) {
            throw new BaseException(ErrorCode.SEAT_NOT_AVAILABLE);
        }

        Seat seat = seatRepository.findByIdWithLock(seatId)
                .orElseThrow(() -> new BaseException(ErrorCode.SEAT_NOT_FOUND));

        seat.reserve();

        return seatId;
    }

    @Transactional
    public void release(Long seatHoldId) {
        SeatHold seatHold = seatHoldRepository.findById(seatHoldId)
                .orElseThrow(() -> new BaseException(ErrorCode.HOLD_NOT_FOUND));

        seatHold.release();
    }

    @Transactional
    public void confirm(Long seatHoldId, Long userId) {
        SeatHold seatHold = seatHoldRepository.findById(seatHoldId)
                .orElseThrow(() -> new BaseException(ErrorCode.HOLD_NOT_FOUND));

        if (!seatHold.getUser().getId().equals(userId)) {
            throw new BaseException(ErrorCode.HOLD_USER_MISMATCH);
        }

        seatHold.confirm();

        ticketRepository.save(Ticket.create(seatHold.getUser(), seatHold.getSeat(), seatHold.getConcert()));
    }
}