package com.idt.aio.request;

import com.idt.aio.entity.constant.State;

public record DocumentStatusResponse(
        String jobId,
        State state,
        Integer docId,
        long timestamp
) {
}
