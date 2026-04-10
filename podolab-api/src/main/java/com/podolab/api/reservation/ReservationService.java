package com.podolab.api.reservation;

import com.podolab.api.global.exception.BaseException;
import com.podolab.api.global.exception.ErrorCode;
import com.podolab.api.seat.Seat;
import com.podolab.api.seat.SeatRepository;
import com.podolab.api.ticket.Ticket;
import com.podolab.api.ticket.TicketRepository;
import com.podolab.api.user.User;
import com.podolab.api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private static final String SEAT_HOLD_KEY_PREFIX = "concerts:%d:seats:%d:hold";
    private static final Duration SEAT_HOLD_TTL = Duration.ofMinutes(5);

    private final SeatRepository seatRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
    public void hold(Long userId, Long concertId, int seatNumber) {
        String redisKey = SEAT_HOLD_KEY_PREFIX.formatted(concertId, seatNumber);

		// 키가 없으면 저장 후 true 반환 / 키가 있으면 false 반환
		Boolean acquired = redisTemplate.opsForValue()
			.setIfAbsent(redisKey, String.valueOf(userId), SEAT_HOLD_TTL);

        if (Boolean.FALSE.equals(acquired)) {
            throw new BaseException(ErrorCode.SEAT_NOT_AVAILABLE);
        }

        seatRepository.findByConcertIdAndSeatNumberWithLock(concertId, seatNumber)
                .orElseThrow(() -> new BaseException(ErrorCode.SEAT_NOT_FOUND));
    }

    @Transactional
    public void release(Long concertId, int seatNumber, Long userId) {
        String redisKey = SEAT_HOLD_KEY_PREFIX.formatted(concertId, seatNumber);

        String storedUserId = redisTemplate.opsForValue().get(redisKey);
        if (storedUserId == null) {
            throw new BaseException(ErrorCode.HOLD_NOT_FOUND);
        }
        if (!storedUserId.equals(String.valueOf(userId))) {
            throw new BaseException(ErrorCode.HOLD_USER_MISMATCH);
        }

        redisTemplate.delete(redisKey);

        seatRepository.findByConcertIdAndSeatNumberWithLock(concertId, seatNumber)
                .orElseThrow(() -> new BaseException(ErrorCode.SEAT_NOT_FOUND));
    }

    @Transactional
    public void confirm(Long concertId, int seatNumber, Long userId) {
        String redisKey = SEAT_HOLD_KEY_PREFIX.formatted(concertId, seatNumber);

        String storedUserId = redisTemplate.opsForValue().get(redisKey);
        if (storedUserId == null) {
            throw new BaseException(ErrorCode.HOLD_NOT_FOUND);
        }
        if (!storedUserId.equals(String.valueOf(userId))) {
            throw new BaseException(ErrorCode.HOLD_USER_MISMATCH);
        }

        redisTemplate.delete(redisKey);

        Seat seat = seatRepository.findByConcertIdAndSeatNumberWithLock(concertId, seatNumber)
                .orElseThrow(() -> new BaseException(ErrorCode.SEAT_NOT_FOUND));
        seat.confirm();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));
        ticketRepository.save(Ticket.create(user, seat, seat.getConcert()));
    }
}