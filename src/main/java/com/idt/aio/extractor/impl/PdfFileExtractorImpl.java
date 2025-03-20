package com.idt.aio.extractor.impl;

import com.idt.aio.extractor.AbstractFileExtractor;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class PdfFileExtractorImpl extends AbstractFileExtractor {


    @Override
    public Resource extractFilePagesAsResource(MultipartFile file, int startPage, int endPage) {
        // 원본 파일 이름 추출 및 검증
        String originalFilename = file.getOriginalFilename();

        // PDF 확장자가 없는 경우 추가
        if (!originalFilename.toLowerCase().endsWith(".pdf")) {
            originalFilename += ".pdf";
        }

        // 필요에 따라 "extracted_" 접두어를 붙이려면 아래처럼 적용 가능
        originalFilename = "extracted_" + originalFilename;


        try (PDDocument originalDocument = PDDocument.load(file.getInputStream());
             PDDocument extractedDocument = new PDDocument()) {

            int totalPages = originalDocument.getNumberOfPages();
            if (startPage < 1 || endPage > totalPages || startPage > endPage) {
                throw new IllegalArgumentException("잘못된 페이지 범위입니다. (전체 페이지: " + totalPages + ")");
            }

            // startPage부터 endPage까지 페이지 복사 (PDFBox의 페이지 인덱스는 0부터 시작)
            for (int i = startPage - 1; i < endPage; i++) {
                PDPage page = originalDocument.getPage(i);
                // addPage 대신 importPage 사용
                extractedDocument.importPage(page);
            }

            // 추출된 페이지들을 메모리 상의 PDF 파일로 저장
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            extractedDocument.save(baos);
            byte[] extractedPdfBytes = baos.toByteArray();

            // ByteArrayResource를 사용하여 Resource 타입으로 반환 (파일 이름을 originalFilename으로 지정)
            final String filename = originalFilename;
            return new ByteArrayResource(extractedPdfBytes) {
                @Override
                public String getFilename() {
                    return filename;
                }
            };
        } catch (IOException e) {
            throw new RuntimeException("PDF 파일에서 페이지를 추출하는 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public int getTotalPages(MultipartFile file) {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            return document.getNumberOfPages();
        } catch (IOException e) {
            throw new RuntimeException("PDF 파일 처리 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public String getSupportedExtension() {
        return "pdf";
    }
}
