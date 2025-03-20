package com.idt.aio.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.data.domain.Sort;

@Builder
public record HomonymPageRequest(
        @NotNull
        @Schema(name = "project_id")
        Integer projectId,
        @NotNull
        @Schema(name = "page")
        int page,
        @NotNull
        @Schema(name = "size")
        int size,
        @NotNull
        @Schema(name = "direction", description = "ASC, DESC")
        Sort.Direction direction,
        @NotNull
        @Schema(name = "sort_property", description = "synonymId, source, match")
        String sortProperty
) {
}
