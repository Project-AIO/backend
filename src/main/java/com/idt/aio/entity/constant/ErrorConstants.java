package com.idt.aio.entity.constant;

import com.idt.aio.error.ErrorResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorConstants {
    NO_ERROR(ErrorResponse.of(null,""));

    private final ErrorResponse error;
}
