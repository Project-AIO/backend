package com.idt.aio.dto;

import com.idt.aio.response.ImageResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentImageDto {
    private Integer docId;
    private Integer page;

    public static List<DocumentImageDto> from(final ImageResponse imageResponse) {
        return imageResponse.getFileDtos().stream()
                .map(imageFileDto -> DocumentImageDto.builder()
                        .docId(imageResponse.getDocId())
                        .page(imageFileDto.getPage())
                        .build())
                .toList();
    }

}
