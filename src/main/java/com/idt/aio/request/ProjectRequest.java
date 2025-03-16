package com.idt.aio.request;

import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ProjectRequest(
        @Size(min = 1, max = 100)
        String name
) {
}
