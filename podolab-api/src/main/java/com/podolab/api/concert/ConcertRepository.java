package com.podolab.api.concert;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ConcertRepository extends JpaRepository<Concert, Long> {

    @Query("SELECT c FROM Concert c WHERE c.openAt BETWEEN :from AND :to")
    List<Concert> findByOpenAtBetween(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);
}
