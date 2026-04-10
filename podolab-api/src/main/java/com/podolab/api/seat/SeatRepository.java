package com.podolab.api.seat;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;

public interface SeatRepository extends JpaRepository<Seat, Long> {

	List<Seat> findByConcertId(Long concertId);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT s FROM Seat s WHERE s.concert.id = :concertId AND s.seatNumber = :seatNumber")
	Optional<Seat> findByConcertIdAndSeatNumberWithLock(@Param("concertId") Long concertId, @Param("seatNumber") int seatNumber);
}