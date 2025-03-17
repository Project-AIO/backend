package com.idt.aio.dto;

import com.idt.aio.response.ImagePageResponse;
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
    private Integer page;

    public static List<DocumentImageDto> from(final ImagePageResponse imagePageResponse) {
        return imagePageResponse.getFileDtos().stream()
                .map(imageFileDto -> DocumentImageDto.builder()
                        .docId(imagePageResponse.getDocId())
                        .page(imageFileDto.getPage())
                        .build())
                .toList();
    }

}
