package com.idt.aio.request;

import lombok.Builder;
import java.util.List;

@Builder
public record ContentSenderRequest(
        List<ContentData> contents,
        String filePath,
        String fileName,
        Integer docId
) {
}
