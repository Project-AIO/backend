package com.idt.aio.entity.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public enum Folder {
    PROJECT("project_"),
    PROJECT_FOLDER("project_folder_"),
    DOCUMENT("document_");

    private final String folderName;
    public String getFolderName(final Integer id) {
        return folderName + id;
    }

    public String getProjectName(final Integer projectId) {
        return folderName + projectId;
    }

    public String getProjectFolderName(final Integer projectId, final Integer projectFolderId) {
        return getProjectName(projectId) + "/" + folderName + projectFolderId;
    }

    public String getDocumentName(final Integer projectId, final Integer projectFolderId, final Integer documentId) {
        return getProjectFolderName(projectId, projectFolderId) + "/" + folderName + documentId;
    }
}
