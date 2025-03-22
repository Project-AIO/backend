package com.idt.aio.entity.sealed;

import java.io.File;

public sealed interface Folder permits Folder.Project, Folder.ProjectFolder, Folder.Document {

    String getFolderPrefix();

    // PROJECT 타입: 프로젝트 경로 생성 (한 개의 파라미터)
    final class Project implements Folder {
        private static final Project INSTANCE = new Project();
        private static final String PREFIX = "project_";

        private Project() {}

        public static Project getInstance() {
            return INSTANCE;
        }

        @Override
        public String getFolderPrefix() {
            return PREFIX;
        }

        public String getPath(Integer projectId) {
            if (projectId == null) {
                throw new IllegalArgumentException("projectId must not be null");
            }
            return PREFIX + projectId;
        }
    }

    // PROJECT_FOLDER 타입: 프로젝트 폴더 경로 생성 (두 개의 파라미터)
    final class ProjectFolder implements Folder {
        private static final ProjectFolder INSTANCE = new ProjectFolder();
        private static final String PREFIX = "project_folder_";

        private ProjectFolder() {}

        public static ProjectFolder getInstance() {
            return INSTANCE;
        }

        @Override
        public String getFolderPrefix() {
            return PREFIX;
        }

        public String getPath(Integer projectId, Integer projectFolderId) {
            if (projectId == null || projectFolderId == null) {
                throw new IllegalArgumentException("projectId and projectFolderId must not be null");
            }
            // PROJECT 타입의 getPath를 재사용하여 경로 생성
            String projectPath = Project.getInstance().getPath(projectId);
            return projectPath + File.separator + PREFIX + projectFolderId;
        }
    }

    // DOCUMENT 타입: 문서 경로 생성 (세 개의 파라미터)
    final class Document implements Folder {
        private static final Document INSTANCE = new Document();
        private static final String PREFIX = "document_";

        private Document() {}

        public static Document getInstance() {
            return INSTANCE;
        }

        @Override
        public String getFolderPrefix() {
            return PREFIX;
        }

        public String getPath(Integer projectId, Integer projectFolderId, Integer documentId) {
            if (projectId == null || projectFolderId == null || documentId == null) {
                throw new IllegalArgumentException("projectId, projectFolderId and documentId must not be null");
            }
            // PROJECT_FOLDER 타입의 getPath를 재사용하여 경로 생성
            String projectFolderPath = ProjectFolder.getInstance().getPath(projectId, projectFolderId);
            return projectFolderPath + File.separator + PREFIX + documentId;
        }
    }
}

