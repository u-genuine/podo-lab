package com.podolab.api.concert;

import com.podolab.api.global.exception.BaseException;
import com.podolab.api.global.exception.ErrorCode;
import com.podolab.api.seat.SeatRepository;
import com.podolab.api.seat.SeatResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ConcertService {

    private static final String SEAT_CACHE_KEY_PREFIX = "concerts:%d:seats";
    private static final Duration SEAT_CACHE_TTL = Duration.ofHours(1);

    private final ConcertRepository concertRepository;
    private final SeatRepository seatRepository;
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional(readOnly = true)
    public List<SeatResponse> getSeats(Long concertId) {
        if (!concertRepository.existsById(concertId)) {
            throw new BaseException(ErrorCode.CONCERT_NOT_FOUND);
        }

        String cacheKey = SEAT_CACHE_KEY_PREFIX.formatted(concertId);
		HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        Map<String, String> cached = hashOps.entries(cacheKey);

		// Cache Hit
        if (!cached.isEmpty()) {
            return cached.entrySet().stream()
                    .map(e -> new SeatResponse(
                            Integer.parseInt(e.getKey()), // field = seatNumber
                            "true".equals(e.getValue()) 		   // value = available
                    ))
                    .sorted(Comparator.comparingInt(SeatResponse::seatNumber))
                    .toList();
        }

		// Cache Miss
		// 1. DB 조회
        List<SeatResponse> seats = seatRepository.findByConcertId(concertId).stream()
                .map(SeatResponse::of)
                .toList();

		// 2. Redis 저장
        Map<String, String> hashEntries = new HashMap<>();
        seats.forEach(s -> hashEntries.put(String.valueOf(s.seatNumber()), String.valueOf(s.available())));
        redisTemplate.opsForHash().putAll(cacheKey, hashEntries);
        redisTemplate.expire(cacheKey, SEAT_CACHE_TTL);

        return seats;
    }
}