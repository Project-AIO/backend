package com.idt.aio.response;

import com.idt.aio.service.DocumentService.ImageData;
import java.util.List;
import lombok.Builder;

@Builder
public record ImageFileResponse(
        Integer docId,
        List<ImageData> imageData

) {
}
