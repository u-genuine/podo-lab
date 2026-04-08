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
    SEAT_NOT_RESERVED(HttpStatus.CONFLICT, "예약 확정된 좌석이 아닙니다."),

    // Hold
    HOLD_NOT_FOUND(HttpStatus.NOT_FOUND, "점유 정보가 없거나 만료되었습니다."),
    HOLD_USER_MISMATCH(HttpStatus.FORBIDDEN, "점유한 사용자가 아닙니다."),

    // Not Found
    CONCERT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 콘서트입니다."),
    SEAT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 좌석입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다.");

    private final HttpStatus status;
    private final String message;
}