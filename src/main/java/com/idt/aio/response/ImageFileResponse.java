package com.idt.aio.response;

import java.util.List;
import lombok.Builder;

@Builder
public record ImageFileResponse(
        List<Integer> pages

) {
}
