package com.idt.aio.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileDto {
    @NotNull
    MultipartFile file;
    @NotNull
    Integer projectFolderId;
    @Size(min = 1, max = 1000)
    @NotNull
    int startPage;
    @Size(min = 1, max = 1000)
    @NotNull
    int endPage;

    public static FileDto from(final MultipartFile file, final Integer endPage, final Integer projectFolderId) {
        return FileDto.builder()
                .file(file)
                .projectFolderId(projectFolderId)
                .startPage(1)
                .endPage(endPage)
                .build();
    }
}
