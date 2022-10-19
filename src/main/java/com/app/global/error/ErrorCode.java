package com.app.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 반환할 http status, 에러 코드, 에러 메세지 관리
 */
@Getter
public enum ErrorCode {
    ;

    ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }

    private HttpStatus httpStatus;
    private String errorCode;
    private String message;
}
