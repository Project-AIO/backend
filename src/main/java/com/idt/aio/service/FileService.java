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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileService {
    static final String PROJECT_ROOT = "/aio";
    static final String ROOT_PATH = System.getProperty("user.dir");

    @Transactional
    public void createFolder(final String folderName) {
        // 루트에 폴더 생성
        // 루트 디렉토리 경로 설정
        final String rootPath = ROOT_PATH + PROJECT_ROOT;

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
    public void deleteFolder(final String folderName) {
        // 루트 디렉토리 경로 설정
        final String rootPath = System.getProperty("user.dir") + PROJECT_ROOT;

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
    public void saveResourceToFolder(final MultipartFile file, final String filePath, final String fileName) {
        final String rootPath = ROOT_PATH + PROJECT_ROOT + filePath;
        try {
            // 대상 경로 (폴더가 이미 존재한다고 가정)
            Path targetDir = Paths.get(rootPath);

            if (fileName == null || fileName.isEmpty()) {
                throw new IllegalArgumentException("파일명이 유효하지 않습니다.");
            }

            // 대상 파일 경로 생성
            Path targetFile = targetDir.resolve(fileName);

            // 파일이 이미 존재하는지 체크
            if (Files.exists(targetFile)) {
                throw DomainExceptionCode.FILE_NAME_DUPLICATED.newInstance();
            }

            // 파일 복사
            Files.copy(file.getInputStream(), targetFile);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("파일 저장 중 오류 발생", e);
        }
    }

}
