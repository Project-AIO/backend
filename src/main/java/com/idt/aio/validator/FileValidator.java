package com.idt.aio.validator;

import com.idt.aio.entity.constant.Extension;
import com.idt.aio.entity.constant.Mime;
import com.idt.aio.exception.DomainExceptionCode;
import com.idt.aio.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Component
public class FileValidator {
    private final FileService fileService;
    //application-local.yml에서 servlet에 정의된 max-file-size 정의된 최대 파일 크기
    @Value("${spring.servlet.multipart.max-file-size}")
    private DataSize MAX_FILE_SIZE; // 50MB

    public void validateFileSize(final MultipartFile file) {

        String contentType = file.getContentType();
        // Mime 타입 확인
        if (!Mime.isValidMimeType(file.getContentType())) {
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
        if (file.getSize() > MAX_FILE_SIZE.toBytes()) {
            throw DomainExceptionCode.FILE_SIZE_EXCEEDED.newInstance(MAX_FILE_SIZE.toBytes());
        }
    }
}
