package com.idt.aio.request;

import com.idt.aio.dto.DocumentData;
import com.idt.aio.dto.DocumentDto;
import com.idt.aio.dto.FileDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
