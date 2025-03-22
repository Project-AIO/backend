package com.idt.aio.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record ProjectAccountRequest (
        @NotNull
        @JsonProperty(value = "project_id")
        Integer projectId,

        @NotNull
        @JsonProperty(value = "account_list")
        List accountList) {
}
