package com.zerobase.zerobasetableing.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionHandler {

    // 예외 발생 시 response 로 CustomException 의 내용을 담기 위해 설정
    @org.springframework.web.bind.annotation.ExceptionHandler({CustomException.class})
    public ResponseEntity<CustomException.CustomExceptionResponse> exceptionHandler(
            HttpServletRequest request,
            final CustomException exception ) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(CustomException.CustomExceptionResponse.builder()
                        .message(exception.getMessage())
                        .code(exception.getErrorCode().name())
                        .status(exception.getStatus())
                        .build());

    }

}
