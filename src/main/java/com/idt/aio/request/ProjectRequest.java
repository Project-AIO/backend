package com.idt.aio.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ProjectRequest(
        @Size(min = 1, max = 100)
        @Schema(name = "name")
        String name
) {
}
