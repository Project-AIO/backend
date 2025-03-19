package com.idt.aio.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.data.domain.Sort;

@Builder
public record SynonymPageRequest(
        @NotNull
        Integer projectId,
        @NotNull
        int page,
        @NotNull
        int size,
        @NotNull
        Sort.Direction direction,
        @NotNull
        String sortProperty
) {
}
