package com.idt.aio.validator;

import com.idt.aio.entity.constant.Extension;
import com.idt.aio.exception.DomainExceptionCode;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileValidator {
    private static final long MAX_FILE_SIZE = 50 * 1024 * 1024; // 50MB

    public void validateFileSize(final MultipartFile file) {
        // PDF 파일인지 확인
        if (!Extension.getValues().contains(file.getContentType())) {
            throw DomainExceptionCode.DOCUMENT_TYPE_INVALID.newInstance();
        }

        //사이즈 체크
        if (file.getSize() > MAX_FILE_SIZE) {
            throw DomainExceptionCode.FILE_SIZE_EXCEEDED.newInstance(MAX_FILE_SIZE);
        }
    }
}
