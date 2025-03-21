package com.idt.aio.repository.custom;

import com.idt.aio.response.DocumentData;
import com.idt.aio.response.QDocumentData;
import com.idt.aio.response.QDocumentFileData;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.idt.aio.entity.QDocument.document;
import static com.idt.aio.entity.QDocumentFile.documentFile;
import static com.idt.aio.entity.QProject.project;
import static com.idt.aio.entity.QProjectFolder.projectFolder;

@Repository
@RequiredArgsConstructor
public class CustomDocumentRepositoryImpl implements CustomDocumentRepository {
    private final JPAQueryFactory queryFactory;
    @Override
    public List<DocumentData> findDocumentDataByProjectId(Integer projectId) {

        return queryFactory
                .select(new QDocumentData(
                        document.docId,
                        projectFolder.projectFolderId,
                        document.name,
                        document.state,
                        new QDocumentFileData(
                                documentFile.docFileId,
                                documentFile.extension,
                                documentFile.path,
                                documentFile.fileName,
                                documentFile.originalFileName,
                                documentFile.totalPage,
                                documentFile.revision
                        ),
                        document.uploadDt
                ))
                .from(project)
                .join(projectFolder).on(project.projectId.eq(projectFolder.project.projectId))
                .join(document).on(projectFolder.projectFolderId.eq(document.projectFolder.projectFolderId))
                .leftJoin(documentFile).on(document.docId.eq(documentFile.document.docId))
                .where(project.projectId.eq(projectId))
                .fetch();
    }

    @Override
    public Optional<DocumentData> findDocumentDataById(Integer docId) {
        final DocumentData documentData = queryFactory
                .select(new QDocumentData(
                        document.docId,
                        document.projectFolder.projectFolderId,
                        document.name,
                        document.state,
                        new QDocumentFileData(
                                documentFile.docFileId,
                                documentFile.extension,
                                documentFile.path,
                                documentFile.fileName,
                                documentFile.originalFileName,
                                documentFile.totalPage,
                                documentFile.revision
                        ),
                        document.uploadDt
                ))
                .from(document)
                .leftJoin(documentFile).on(document.docId.eq(documentFile.document.docId))
                .where(document.docId.eq(docId))
                .fetchOne();

        return Optional.ofNullable(documentData);
    }
}
