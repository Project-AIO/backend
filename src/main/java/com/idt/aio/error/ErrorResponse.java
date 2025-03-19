package com.idt.aio.error;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

    private Integer code;
    private String message;

    public static ErrorResponse of(Integer code, String message) {
        return new ErrorResponse(code, message);
    }
}
