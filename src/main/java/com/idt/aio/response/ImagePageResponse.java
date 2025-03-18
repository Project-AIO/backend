package com.idt.aio.response;

import java.util.List;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImagePageResponse {
    //images resource와 page 정보를 담고 있는 dto
    private List<Integer> pages;
}
