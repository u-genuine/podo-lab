-- 기존 데이터 초기화
DELETE FROM seat;
DELETE FROM concert;

-- 콘서트 1개 삽입
INSERT INTO concert (title, concert_date, total_seats, created_at, updated_at)
VALUES ('PODOLAB 콘서트 2026', '2026-08-01', 10000, NOW(), NOW());

-- 좌석 10000개 삽입 (seatNumber 1~10000, 전부 AVAILABLE)
DROP PROCEDURE IF EXISTS insert_seats;

DELIMITER $$
CREATE PROCEDURE insert_seats()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE concert_id BIGINT;

SELECT id INTO concert_id FROM concert LIMIT 1;

WHILE i <= 10000 DO
        INSERT INTO seat (concert_id, seat_number, status, created_at, updated_at)
        VALUES (concert_id, i, 'AVAILABLE', NOW(), NOW());
        SET i = i + 1;
END WHILE;
END$$
DELIMITER ;

CALL insert_seats();
DROP PROCEDURE IF EXISTS insert_seats;