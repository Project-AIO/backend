package com.idt.aio.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum JwtExceptionCode {
    JWT(404, "정의되지 않은 에러입니다."),
    JWT_UNAUTHORIZED(401, "Unauthorized, JWT 토큰이 유효하지 않습니다."),
    JWT_NO_AUTHORIZATION_HEADER(JWT.code + 1, "Unauthorized, JWT 토큰이 없습니다."),
    ;
    private final int code;
    private final String message;

    public JwtException newInstance() {
        return new JwtException(code, message);
    }

    public JwtException newInstance(Throwable ex) {
        return new JwtException(code, message, ex);
    }

    public JwtException newInstance(Object... args) {
        return new JwtException(code, String.format(message, args), args);
    }

    public JwtException newInstance(Throwable ex, Object... args) {
        return new JwtException(code, String.format(message, args), ex, args);
    }
}
