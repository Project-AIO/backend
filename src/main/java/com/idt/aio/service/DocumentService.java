package com.idt.aio.service;

import com.idt.aio.dto.DocumentData;
import com.idt.aio.dto.DocumentDto;
import com.idt.aio.dto.DocumentImageDto;
import com.idt.aio.dto.FileDto;
import com.idt.aio.dto.ImageFileDto;
import com.idt.aio.entity.Document;
import com.idt.aio.entity.constant.Folder;
import com.idt.aio.repository.DocumentRepository;
import com.idt.aio.response.ImageFileResponse;
import com.idt.aio.response.ImageResponse;
import com.idt.aio.service.DocumentImageService.ImageDto;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class DocumentService {
    private final DocumentRepository documentRepository;

    private final CoreServerService coreServerService;
    private final FileService fileService;
    private final DocumentImageService documentImageService;

    @Transactional(readOnly = true)
    public List<DocumentDto> fetchDocumentByFolderId(final Integer folderId) {
        final List<Document> documents = documentRepository.findByProjectFolder_ProjectFolderId(
                folderId);

        return DocumentDto.from(documents);
    }

    @Transactional
    public ImageFileResponse fetchImages(final FileDto fileDto, final Integer projectId) {
        final MultipartFile file = fileDto.getFile();
        final int startPage = fileDto.getStartPage();
        final int endPage = fileDto.getEndPage();

        //코어 서버로 전송 후 이미지 파일 반환
        final ImageResponse execute = coreServerService.execute(file, startPage, endPage);

        final List<DocumentImageDto> documentImageDtos = DocumentImageDto.from(execute);

        //코어 서버로부터 받은 이미지가 없다면 (문서에 추출된 이미지가 없다면 바로 반환)
        if (documentImageDtos.isEmpty()) {
            return ImageFileResponse.builder()
                    .imageData(List.of())
                    .build();
        }

        //서버에 파일 저장
        final List<ImageDto> imageDtos = documentImageService.saveDocumentImage(documentImageDtos);

        //무조건 데이터가 1개 이상 있음, response 구성
        final List<ImageData> imageData = relateDocumentImages(execute.getFileDtos(), imageDtos);

        final Integer docId = imageDtos.get(0).docImageId();


        final ImageFileResponse response = ImageFileResponse.builder()
                .docId(docId)
                .imageData(imageData)
                .build();

        //폴더에 이미지 저장
        Integer projectFolderId = fileDto.getProjectFolderId();
        final String folderPath = Folder.DOCUMENT.getDocumentName(projectId,   projectFolderId, docId);
        fileService.saveResourceToFolder(response, folderPath);


        return response;
    }

    // 이미지 데이터와 이미지 파일을 매핑
    private List<ImageData> relateDocumentImages(final List<ImageFileDto> imageFileDtos,
                                                 final List<ImageDto> imageDtos) {
        // imageDtos를 스트림으로 groupingBy를 활용하여 page별 LinkedList로 매핑
        final Map<Integer, LinkedList<Integer>> pageDocIdMap = imageDtos.stream()
                .collect(Collectors.groupingBy(
                        ImageDto::page,
                        Collectors.mapping(ImageDto::docImageId, Collectors.toCollection(LinkedList::new))
                ));

        // imageFileDtos 스트림으로 ImageData 빌드
        return imageFileDtos.stream()
                .map(imageFileDto -> ImageData.builder()
                        .docImageId(pageDocIdMap.get(imageFileDto.getPage()).poll())
                        .imageFile(imageFileDto.getImageFile())
                        .page(imageFileDto.getPage())
                        .build())
                .toList();
    }

    @Transactional
    public void saveDocument(final Document document, final Integer projectId) {

        //db저장
        Document save = documentRepository.save(document);

        //폴더 생성
        fileService.createFolder(
                Folder.DOCUMENT.getDocumentName(projectId, save.getProjectFolder().getProjectFolderId(),
                        save.getDocId()));

    }

    @Builder
    public record ImageData(
            Integer docImageId,
            Resource imageFile,
            int page) {

    }

}
