package com.idt.aio.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponse {
    private List<Resource> images;
    private int startPage;
    private int endPage;
}
