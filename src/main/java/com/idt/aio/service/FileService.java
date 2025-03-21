package com.idt.aio.service;

import com.idt.aio.exception.DomainExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileService {
    static final String PROJECT_ROOT = File.separator + "aio";
    static final String ROOT_PATH = System.getProperty("user.dir");



    @Transactional
    public String createFolder(final String folderName) {
        // 루트에 폴더 생성
        // 루트 디렉토리 경로 설정
        final String rootPath = ROOT_PATH + PROJECT_ROOT;

        // 새 폴더 경로 설정
        final File newFolder = new File(rootPath, folderName);

        // 폴더 생성
        if (!newFolder.exists()) {
            if (newFolder.mkdirs()) {
                log.info("폴더 생성 성공: " + newFolder.getAbsolutePath());
            }
        }
        return newFolder.getAbsolutePath();
    }

    @Transactional(readOnly = true)
    public Resource getFileFromPath(String folderName, String fileName) {
        // ROOT_PATH와 PROJECT_ROOT를 사용하여 루트 디렉토리 경로 구성
        String rootDir = ROOT_PATH + PROJECT_ROOT;

        // 특정 폴더 내의 파일 경로 구성
        Path filePath = Paths.get(rootDir, folderName, fileName);

        // Resource 생성 (파일 시스템 기반)
        Resource resource = new FileSystemResource(filePath);

        // 파일 존재 여부 체크
        if (!resource.exists() || !resource.isReadable()) {
            throw new RuntimeException("파일을 찾을 수 없거나 읽을 수 없습니다: " + filePath.toString());
        }

        return resource;
    }

    @Transactional
    public void deleteFolder(final String folderName) {
        final String rootPath = System.getProperty("user.dir") + PROJECT_ROOT;
        final File deleteFolder = new File(rootPath, folderName);

        if (deleteFolder.exists()) {
            try {
                FileUtils.deleteDirectory(deleteFolder); // 폴더와 하위 모든 파일/폴더 삭제
                log.info("폴더 및 내부 파일 삭제 성공: " + deleteFolder.getAbsolutePath());
            } catch (IOException e) {
                // 예외 처리
                throw new RuntimeException("폴더 삭제 중 오류 발생", e);
            }
        }
    }

    @Transactional
    public void deleteFolderByAbsolutePath(final String path) {
        final File deleteFolder = new File(path);

        if (deleteFolder.exists()) {
            try {
                FileUtils.deleteDirectory(deleteFolder); // 폴더와 하위 모든 파일/폴더 삭제
                log.info("폴더 및 내부 파일 삭제 성공: " + deleteFolder.getAbsolutePath());
            } catch (IOException e) {
                // 예외 처리
                throw new RuntimeException("폴더 삭제 중 오류 발생", e);
            }
        }
    }

    @Transactional
    public void saveResourceToFolder(final MultipartFile file, final String filePath, final String fileName,
                                     final String extension) {


        try {
            // 대상 경로 (폴더가 이미 존재한다고 가정)
            Path targetDir = Paths.get(filePath);

            if (fileName == null || fileName.isEmpty()) {
                throw new IllegalArgumentException("파일명이 유효하지 않습니다.");
            }

            // 대상 파일 경로 생성
            Path targetFile = targetDir.resolve(String.format(fileName + ".%s", extension));

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


    @Transactional(readOnly = true)
    public String findPathWithoutRootByFolderName(Path rootPath, String folderName) {
        try (Stream<Path> pathStream = Files.walk(rootPath)) {
            String path = Objects.requireNonNull(pathStream
                    .filter(Files::isDirectory)
                    .filter(p -> p.getFileName().toString().equals(folderName))
                    .findFirst()
                    .orElseThrow(DomainExceptionCode.FILE_NOT_FOUND::newInstance)).toString();

            //path에서 ROOT_PATH+PROJECT_ROOT를 제거한 경로 반환
            return path.replace(ROOT_PATH + PROJECT_ROOT + "\\", "");
        } catch (IOException e) {
            throw DomainExceptionCode.FILE_NOT_FOUND.newInstance();
        }
    }

    public String getFileExtension(final MultipartFile file) {
        final String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw DomainExceptionCode.FILE_NAME_IS_NULL.newInstance();
        }
        // FilenameUtils.getExtension()은 파일명에서 마지막 '.' 이후의 문자열을 반환합니다.
        return FilenameUtils.getExtension(originalFilename);
    }

    public String getFileNameWithoutExtension(final MultipartFile file) {
        return FilenameUtils.getBaseName(file.getOriginalFilename());
    }

    public long getFileSize(final MultipartFile file) {
        return file.getSize();
    }

}
