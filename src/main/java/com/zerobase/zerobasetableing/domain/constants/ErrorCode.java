package com.zerobase.zerobasetableing.domain.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "회원이 존재하지 않습니다."),
    NOT_FOUND_STORE(HttpStatus.BAD_REQUEST, "존재하지 않는 음식점입니다."),
    NOT_FOUND_RESERVATION(HttpStatus.BAD_REQUEST, "존재하지 않는 예약입니다."),
    RESERVATION_STORE_NOT_MATCHED(HttpStatus.BAD_REQUEST, "요청하신 음식점의 예약이 아닙니다."),
    ALREADY_ACCEPT(HttpStatus.BAD_REQUEST, "이미 완료된 예약입니다."),
    INVALID_ACCESS(HttpStatus.BAD_REQUEST, "잘못된 접근입니다."),
    STORE_SELLER_NOT_MATCHED(HttpStatus.BAD_REQUEST, "셀러와 음식점이 매치되지 않습니다."),
    UNMATCHED_ID_OR_PASSWORD(HttpStatus.BAD_REQUEST, "ID나 PW가 일치하지 않습니다.")

    ;


    private final HttpStatus httpStatus;
    private final String detail;

}
