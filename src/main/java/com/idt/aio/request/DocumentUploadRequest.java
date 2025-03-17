package com.idt.aio.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
public record DocumentUploadRequest(
        @NotNull
        Integer projectId,
        @NotNull
        Integer projectFolderId,
        @NotNull
        MultipartFile file,
        @NotNull
        @Size(min = 1, max = 100)
        String fileName,
        @NotNull
        List<Integer> removePages,
        @NotNull
        int contentStartPage,
        @NotNull
        int contentEndPage
) {
}
