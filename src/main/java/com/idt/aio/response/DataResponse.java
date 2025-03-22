package com.idt.aio.response;

import com.idt.aio.entity.constant.ErrorConstants;
import com.idt.aio.error.ErrorResponse;
import lombok.Builder;


@Builder
public record DataResponse<T>(
        T data,
        ErrorResponse error
) {
    public static <T> DataResponse<T> of(final T data, final ErrorResponse error) {
        return new DataResponse<>(data, error);
    }

    public static <T> DataResponse<T> from(final T data) {
        return new DataResponse<>(data, ErrorConstants.NO_ERROR.getError());
    }
}
