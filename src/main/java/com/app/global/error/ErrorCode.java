package com.app.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 반환할 http status, 에러 코드, 에러 메세지 관리
 */
@Getter
public enum ErrorCode {
    TEST(HttpStatus.INTERNAL_SERVER_ERROR, "001", "business exception test"),

    //인증 영역
    TOKEN_EPIRED(HttpStatus.UNAUTHORIZED, "A-001", "토큰이 만료 되었습니다."),
    NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "A-001", "해당 토큰은 유효한 토큰이 아닙니다.");

    ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }

    private HttpStatus httpStatus;
    private String errorCode;
    private String message;
}
