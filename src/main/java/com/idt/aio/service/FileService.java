package com.idt.aio.service;

import com.idt.aio.exception.DomainExceptionCode;
import com.idt.aio.response.ImageFileResponse;
import com.idt.aio.service.DocumentService.ImageData;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileService {
    static final String PROJECT_ROOT = "/aio";
    static final String DOCUMENT_IMAGE_NAME = "image_%d";
    static final String ROOT_PATH = System.getProperty("user.dir");

    @Transactional
    public void createFolder(final String folderName)  {
        // 루트에 폴더 생성
        // 루트 디렉토리 경로 설정
        final String rootPath = ROOT_PATH+PROJECT_ROOT;

        // 새 폴더 경로 설정
        final File newFolder = new File(rootPath, folderName);

        // 폴더 생성
        if (!newFolder.exists()) {
            if (newFolder.mkdirs()) {
                log.info("폴더 생성 성공: " + newFolder.getAbsolutePath());
                return;
            }
            throw DomainExceptionCode.FOLDER_CREATION_FAILED.newInstance();
        }
        log.error("폴더가 이미 존재합니다: " + newFolder.getAbsolutePath());
        throw DomainExceptionCode.FOLDER_EXISTS.newInstance();
    }

    @Transactional
    public void deleteFolder(final String folderName){
        // 루트 디렉토리 경로 설정
        final String rootPath = System.getProperty("user.dir")+PROJECT_ROOT;

        // 삭제할 폴더 경로 설정
        final File deleteFolder = new File(rootPath, folderName);

        // 폴더 삭제
        if (deleteFolder.exists()) {
            if (deleteFolder.delete()) {
                log.info("폴더 삭제 성공: " + deleteFolder.getAbsolutePath());
                return;
            }
            throw DomainExceptionCode.FOLDER_CREATION_FAILED.newInstance();
        }
    }

    @Transactional
    public void saveResourceToFolder(final ImageFileResponse imageFiles, final String filePath) {
        final String rootPath = ROOT_PATH + PROJECT_ROOT + filePath;
        try {
            // 대상 경로 (폴더가 이미 존재한다고 가정)
            Path targetDir = Paths.get(rootPath);

            // 리스트에 담긴 각 Resource 처리
            for (ImageData data : imageFiles.imageData()) {
                String filename = String.format(DOCUMENT_IMAGE_NAME,data.docImageId());
                if (filename == null) {
                    // 파일명이 없으면 건너뜁니다.
                    continue;
                }
                // 대상 파일 경로 생성
                Path targetFile = targetDir.resolve(filename);
                // 파일 복사 (기존 파일이 있으면 교체)
                Files.copy(data.imageFile().getInputStream(), targetFile, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("파일 저장 중 오류 발생", e);
        }
    }

}
