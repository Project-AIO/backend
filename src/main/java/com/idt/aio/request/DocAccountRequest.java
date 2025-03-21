package com.idt.aio.request;

import java.util.List;
import lombok.Builder;

@Builder
public record DocAccountRequest(
        Integer docId,
        List<String> accountIds
) {
}
