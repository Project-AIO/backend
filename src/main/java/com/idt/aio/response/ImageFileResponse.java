package com.idt.aio.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ImageFileResponse(
        List<Integer> pages

) {
}
