package com.idt.aio.response;

import com.idt.aio.entity.constant.State;
import lombok.Builder;

@Builder
public record CoreServerResponse(
        Integer docId,
        State state
) {
}
