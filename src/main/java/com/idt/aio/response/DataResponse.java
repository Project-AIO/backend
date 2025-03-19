package com.idt.aio.response;

import com.idt.aio.entity.constant.ErrorConstants;
import com.idt.aio.error.ErrorResponse;
import lombok.Builder;


@Builder
public record DataResponse(
        Object data,
        ErrorResponse error
) {
    public static DataResponse of(final Object data, final ErrorResponse error) {
        return new DataResponse(data, error);
    }

    public static DataResponse from(final Object data) {
        return new DataResponse(data, ErrorConstants.NO_ERROR.getError());
    }
}
