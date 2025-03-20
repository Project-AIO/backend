package com.idt.aio.factory;

import com.idt.aio.extractor.AbstractFileExtractor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class FileExtractorFactory {
    private final Map<String, AbstractFileExtractor> extractorMap;

    public FileExtractorFactory(List<AbstractFileExtractor> extractors) {
        // 각 extractor의 지원 확장자를 키로 하는 Map 생성
        this.extractorMap = extractors.stream()
                .collect(Collectors.toMap(
                        extractor -> extractor.getSupportedExtension().toLowerCase(),
                        Function.identity()
                ));
    }

    public AbstractFileExtractor getExtractor(String extension) {
        AbstractFileExtractor extractor = extractorMap.get(extension.toLowerCase());
        if (extractor == null) {
            throw new IllegalArgumentException("지원하지 않는 파일 형식입니다: " + extension);
        }
        return extractor;
    }
}
