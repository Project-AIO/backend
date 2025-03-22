package com.idt.aio.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record AccountRequest(
        @NotNull
        @JsonProperty(value = "admin_id")
        String adminId,

        @NotNull
        String name,

        @NotNull
        String role) {
}
