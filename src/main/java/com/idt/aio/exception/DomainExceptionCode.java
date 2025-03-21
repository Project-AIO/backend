package com.idt.aio.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

@RequiredArgsConstructor
@Getter
public enum DomainExceptionCode {
    PROJECT_FOLDER(1000, "정의되지 않은 에러입니다."),
    PROJECT_FOLDER_NOT_FOUND(PROJECT_FOLDER.code + 1, "프로젝트 폴더를 찾을 수 없습니다."),


    PROJECT(1500, "정의되지 않은 에러입니다."),
    PROJECT_NOT_FOUND(PROJECT.code + 1, "프로젝트를 찾을 수 없습니다."),

    DOCUMENT(2000, "정의되지 않은 에러입니다."),
    DOCUMENT_TYPE_INVALID(Document.code + 1, "MIME Type이 올바르지 않습니다."),
    DOCUMENT_NOT_FOUND(Document.code + 2, "문서를 찾을 수 없습니다."),

    FILE(2500, "정의되지 않은 에러입니다."),
    FILE_READ_FAILED(FILE.code + 1, "파일을 읽는데 실패했습니다."),
    FILE_SIZE_EXCEEDED(FILE.code + 2, "파일 크기가 %dMB를 초과했습니다."),
    FOLDER_CREATION_FAILED(FILE.code + 3, "폴더를 찾을 수 없습니다."),
    FOLDER_EXISTS(FILE.code + 4, "폴더가 이미 존재합니다."),
    FILE_NAME_DUPLICATED(FILE.code + 5, "중복된 파일명이 있습니다."),
    FILE_NOT_FOUND(FILE.code + 6, "파일을 찾을 수 없습니다."),
    FILE_EXTENSION_INVALID(FILE.code + 7, "파일 확장자가 올바르지 않습니다."),
    FILE_EXTENSION_NOT_ALLOWED(FILE.code + 8, "허용되지 않는 파일 확장자입니다."),
    FILE_NAME_IS_NULL(FILE.code + 9, "파일명이 null입니다."),

    CONFIGURATION_KNOWLEDGE(3000, "정의되지 않은 에러입니다."),
    CONFIGURATION_KNOWLEDGE_NOT_FOUND(CONFIGURATION_KNOWLEDGE.code + 1, "설정 지식을 찾을 수 없습니다."),

    LANGUAGE_MODEL(3500, "정의되지 않은 에러입니다."),
    LANGUAGE_MODEL_NOT_FOUND(LANGUAGE_MODEL.code + 1, "언어 모델을 찾을 수 없습니다."),

    ANSWER(4000, "정의되지 않은 에러입니다."),
    ANSWER_NOT_FOUND(ANSWER.code + 1, "답변을 찾을 수 없습니다."),
    CONVERSATION(4500, "정의되지 않은 에러입니다."),
    CONVERSATION_NOT_FOUND(CONVERSATION.code + 1, "대화를 찾을 수 없습니다."),

    ACCOUNT(5000, "정의되지 않은 에러입니다."),
    ACCOUNT_NOT_FOUND(ACCOUNT.code + 1, "계정을 찾을 수 없습니다."),
    ;


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
