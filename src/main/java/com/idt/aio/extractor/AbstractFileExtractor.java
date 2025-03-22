package com.idt.aio.extractor;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
public abstract class AbstractFileExtractor {
    public abstract Resource extractFilePagesAsResource(final MultipartFile file, final int startPage, final int endPage);

    public abstract int getTotalPages(final MultipartFile file);

    public abstract String getSupportedExtension();


}
