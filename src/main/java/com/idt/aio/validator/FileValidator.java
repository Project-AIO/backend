package com.idt.aio.validator;

import com.idt.aio.entity.constant.Extension;
import com.idt.aio.entity.constant.Mime;
import com.idt.aio.exception.DomainExceptionCode;
import com.idt.aio.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Component
public class FileValidator {
    private static final long MAX_FILE_SIZE = 50 * 1024 * 1024; // 50MB
    private final FileService fileService;

    public void validateFileSize(final MultipartFile file) {
        // Mime 타입 확인
        if (!Mime.getValues().contains(file.getContentType())) {
            throw DomainExceptionCode.DOCUMENT_TYPE_INVALID.newInstance();
        }

        //확장자 확인
        final String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw DomainExceptionCode.FILE_EXTENSION_INVALID.newInstance();
        }
        // FilenameUtils.getExtension()은 파일명에서 마지막 '.' 이후의 문자열을 반환합니다.
        final String extension = fileService.getFileExtension(file);

        if (!Extension.getValues().contains(extension)) {
            throw DomainExceptionCode.FILE_EXTENSION_INVALID.newInstance(extension);
        }

        //사이즈 체크
        if (file.getSize() > MAX_FILE_SIZE) {
            throw DomainExceptionCode.FILE_SIZE_EXCEEDED.newInstance(MAX_FILE_SIZE);
        }
    }
}
