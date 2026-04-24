package com.podolab.api.concert;

import com.podolab.api.seat.SeatRepository;
import com.podolab.api.seat.SeatStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class CacheWarmingScheduler {

    private static final String SEAT_CACHE_KEY_PREFIX = "concerts:%d:seats";
    private static final Duration SEAT_CACHE_TTL = Duration.ofHours(1);

    private final ConcertRepository concertRepository;
    private final SeatRepository seatRepository;
    private final RedisTemplate<String, String> redisTemplate;

    @Scheduled(cron = "0 * * * * *")
    @Transactional(readOnly = true)
    public void warmUpSeatCache() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime until = now.plusMinutes(5);

        List<Concert> upcoming = concertRepository.findByOpenAtBetween(now, until);
        if (upcoming.isEmpty()) {
            return;
        }

        for (Concert concert : upcoming) {
            String cacheKey = SEAT_CACHE_KEY_PREFIX.formatted(concert.getId());

            if (Boolean.TRUE.equals(redisTemplate.hasKey(cacheKey))) {
                log.debug("seat cache already exists, skip warming: concertId={}", concert.getId());
                continue;
            }

            Map<String, String> hashEntries = new HashMap<>();
            seatRepository.findByConcertId(concert.getId())
                    .forEach(seat -> hashEntries.put(
                            String.valueOf(seat.getSeatNumber()),
                            String.valueOf(seat.getStatus() == SeatStatus.AVAILABLE)
                    ));

            redisTemplate.opsForHash().putAll(cacheKey, hashEntries);
            redisTemplate.expire(cacheKey, SEAT_CACHE_TTL);

            log.info("seat cache warmed up: concertId={}, seats={}", concert.getId(), hashEntries.size());
        }
    }
}