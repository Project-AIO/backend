package com.idt.aio.response;

import lombok.Builder;

@Builder
public record ContentResponse(
        String title,
        int startPage,
        int endPage
) {
    //images resource와 page 정보를 담고 있는 dto

}
