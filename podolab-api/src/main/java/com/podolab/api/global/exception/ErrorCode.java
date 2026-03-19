package com.podolab.api.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "잘못된 입력값입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),

    // Seat
    SEAT_NOT_AVAILABLE(HttpStatus.CONFLICT, "예약 가능한 좌석이 아닙니다."),
    SEAT_NOT_HELD(HttpStatus.CONFLICT, "선점되지 않은 좌석입니다."),
    SEAT_NOT_RESERVED(HttpStatus.CONFLICT, "예약 확정된 좌석이 아닙니다."),

    // SeatHold
    HOLD_NOT_ACTIVE(HttpStatus.CONFLICT, "유효한 점유 상태가 아닙니다.");

    private final HttpStatus status;
    private final String message;
}