package com.idt.aio.response;

import com.idt.aio.dto.ImageFileDto;
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
    //images resource와 page 정보를 담고 있는 dto
    private List<ImageFileDto> fileDtos;
    //어떤 페이지에 해당되는 이미지 파일인지
    private Integer docId;
}
