package com.idt.aio.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImagePageResponse {
    //images resource와 page 정보를 담고 있는 dto
    private List<Integer> pages;
}
