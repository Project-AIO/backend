package com.idt.aio.entity.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;
@Deprecated
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public enum Folder {
    PROJECT("project_"),
    PROJECT_FOLDER("project_folder_"),
    DOCUMENT("document_");
    private final String ROOT = System.getProperty("user.dir");
    private final String folderName;

    public String getFolderName(final Integer id) {
        return PROJECT.folderName + id;
    }

    public String getProjectPath(final Integer projectId) {
        return PROJECT.folderName + projectId;
    }

    public String getProjectFolderPath(final Integer projectId, final Integer projectFolderId) {
        return getProjectPath(projectId) + File.separator + PROJECT_FOLDER.folderName + projectFolderId;
    }

    public String getDocumentFolderPath(final Integer projectId, final Integer projectFolderId, final Integer documentId) {
        return getProjectFolderPath(projectId, projectFolderId) + File.separator + DOCUMENT.folderName + documentId;
    }

}
