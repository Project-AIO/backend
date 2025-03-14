package com.idt.aio.exception;

import java.util.function.Supplier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DomainExceptionCode {
    PROJECT_FOLDER(1000, "정의되지 않은 에러입니다."),
    PROJECT_FOLDER_NOT_FOUND(PROJECT_FOLDER.code + 1, "프로젝트 폴더를 찾을 수 없습니다."),


    PROJECT(1500, "정의되지 않은 에러입니다."),
    PROJECT_NOT_FOUND(PROJECT.code + 1, "프로젝트를 찾을 수 없습니다.");

    private final int code;
    private final String message;

    public DomainException newInstance() {
        return new DomainException(code, message);
    }

    public DomainException newInstance(Throwable ex) {
        return new DomainException(code, message, ex);
    }

    public DomainException newInstance(Object... args) {
        return new DomainException(code, String.format(message, args), args);
    }

    public DomainException newInstance(Throwable ex, Object... args) {
        return new DomainException(code, String.format(message, args), ex, args);
    }

    public void invokeBySupplierCondition(Supplier<Boolean> condition) {
        if (condition.get()) {
            throw new DomainException(code, message);
        }
    }

    public void invokeByCondition(boolean condition) {
        if (condition) {
            throw new DomainException(code, message);
        }
    }
}
