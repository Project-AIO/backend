package com.idt.aio.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record DocumentUploadRequest(
        @NotNull
        Integer projectId,
        @NotNull
        Integer projectFolderId,
        @NotNull
        MultipartFile file
) {
}
