package com.zerobase.zerobasetableing.exception;

import com.zerobase.zerobasetableing.domain.constants.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getDetail());
        this.errorCode = errorCode;
    }

    ErrorCode errorCode;

}
