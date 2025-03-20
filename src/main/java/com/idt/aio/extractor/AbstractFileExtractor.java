package com.idt.aio.extractor;

import com.idt.aio.entity.Document;
import com.idt.aio.entity.ProjectFolder;
import com.idt.aio.entity.constant.Folder;
import com.idt.aio.entity.constant.State;
import com.idt.aio.exception.DomainExceptionCode;
import com.idt.aio.repository.ProjectFolderRepository;
import com.idt.aio.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
public abstract class AbstractFileExtractor {
    public abstract Resource extractFilePagesAsResource(final MultipartFile file, final int startPage, final int endPage);
    public abstract int getTotalPages(final MultipartFile file);
    public abstract String getSupportedExtension();


}
