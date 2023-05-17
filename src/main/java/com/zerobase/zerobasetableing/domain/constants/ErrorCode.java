package com.zerobase.zerobasetableing.domain.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "회원이 존재하지 않습니다."),
    NOT_FOUND_STORE(HttpStatus.BAD_REQUEST, "존재하지 않는 음식점입니다."),
    UNMATCHED_ID_OR_PASSWORD(HttpStatus.BAD_REQUEST, "ID나 PW가 일치하지 않습니다.")

    ;


    private final HttpStatus httpStatus;
    private final String detail;

}
