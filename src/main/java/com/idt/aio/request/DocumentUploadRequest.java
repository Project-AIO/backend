package com.idt.aio.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record DocumentUploadRequest(
        @NotNull
        MultipartFile file,
        @Size(min = 1, max = 1000)
        @NotNull
        int startPage,
        @Size(min = 1, max = 1000)
        @NotNull
        int endPage
) {
}
