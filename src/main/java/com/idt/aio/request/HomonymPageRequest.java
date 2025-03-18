package com.idt.aio.request;

import lombok.Builder;
import org.springframework.data.domain.Sort;

@Builder
public record HomonymPageRequest(
        Integer projectId,
        int page,
        int size,
        Sort.Direction direction,
        String sortProperty
) {
}
