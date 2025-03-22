package com.idt.aio.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Getter
public abstract class BaseCustomException extends RuntimeException {

    private final int code;

    private final List<Object> args;

    public BaseCustomException(int code, String message, Throwable ex) {
        super(message, ex);
        this.code = code;
        this.args = new ArrayList<>();
    }

    public BaseCustomException(int code, String message, Object... args) {
        super(message);
        this.code = code;
        this.args = Arrays.asList(args);
    }

    public BaseCustomException(int code, String message) {
        super(message);
        this.code = code;
        this.args = new ArrayList<>();
    }


}
