SET FOREIGN_KEY_CHECKS = 0;

-- 1. tb_admin (본 시스템 관리자 계정 정보)
DROP TABLE IF EXISTS `tb_admin`;
CREATE TABLE IF NOT EXISTS `tb_admin` (
    `admin_id` VARCHAR(255) NOT NULL,
    `pw` VARCHAR(30),
    `license_key` INT,
    PRIMARY KEY (`admin_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2. tb_project (프로젝트 - 데이터 관리 최상단 레벨)
DROP TABLE IF EXISTS `tb_project`;
CREATE TABLE IF NOT EXISTS `tb_project` (
                                            `project_id` INT NOT NULL AUTO_INCREMENT,
                                            `name` VARCHAR(100) NOT NULL,
    `create_dt` DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`project_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 10. tb_homonym (이의어)
DROP TABLE IF EXISTS `tb_homonym`;
CREATE TABLE IF NOT EXISTS `tb_homonym` (
                                            `homonym_id` INT NOT NULL AUTO_INCREMENT,
                                            `project_id` INT NOT NULL,
                                            `source` VARCHAR(25) NOT NULL,
    `match` VARCHAR(25) NOT NULL,
    PRIMARY KEY (`homonym_id`),
    FOREIGN KEY (`project_id`) REFERENCES `tb_project`(`project_id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 9. tb_synonym (동의어)
DROP TABLE IF EXISTS `tb_synonym`;
CREATE TABLE IF NOT EXISTS `tb_synonym` (
                                            `synonym_id` INT NOT NULL AUTO_INCREMENT,
                                            `project_id` INT NOT NULL,
                                            `source` VARCHAR(25) NOT NULL,
    `match` VARCHAR(25) NOT NULL,
    PRIMARY KEY (`synonym_id`),
    FOREIGN KEY (`project_id`) REFERENCES `tb_project`(`project_id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3. tb_account (관리자가 생성한 계정)
DROP TABLE IF EXISTS `tb_account`;
CREATE TABLE IF NOT EXISTS `tb_account` (
    `account_id` VARCHAR(255) NOT NULL,
    `admin_id` VARCHAR(255),
    `role` ENUM('USER','GUEST') DEFAULT 'USER' NOT NULL,
    `name` VARCHAR(30) NOT NULL,
    PRIMARY KEY (`account_id`),
    FOREIGN KEY (`admin_id`) REFERENCES `tb_admin`(`admin_id`) ON DELETE SET NULL ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 4. tb_project_folder (프로젝트 폴더 - 프로젝트 하위 데이터 포함)
DROP TABLE IF EXISTS `tb_project_folder`;
CREATE TABLE IF NOT EXISTS `tb_project_folder` (
                                                   `project_folder_id` INT NOT NULL AUTO_INCREMENT,
                                                   `project_id` INT NOT NULL,
                                                   `name` VARCHAR(255) NOT NULL,
    `create_dt` DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`project_folder_id`),
    FOREIGN KEY (`project_id`) REFERENCES `tb_project`(`project_id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 5. tb_project_account (프로젝트에 접근 가능한 계정의 참조 테이블)
DROP TABLE IF EXISTS `tb_project_account`;
CREATE TABLE IF NOT EXISTS `tb_project_account` (
                                                    `project_account_id` INT NOT NULL AUTO_INCREMENT,
                                                    `project_id` INT NOT NULL,
                                                    `account_id` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`project_account_id`),
    FOREIGN KEY (`project_id`) REFERENCES `tb_project`(`project_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`account_id`) REFERENCES `tb_account`(`account_id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 6. tb_lang_model (언어 모델)
DROP TABLE IF EXISTS `tb_lang_model`;
CREATE TABLE IF NOT EXISTS `tb_lang_model` (
                                               `lang_model_id` INT NOT NULL AUTO_INCREMENT,
                                               `project_id` INT NOT NULL,
                                               `name` VARCHAR(255) NOT NULL,
    `vendor` VARCHAR(255),
    `feature` ENUM('CHAT','EMBEDDING','RERANK') DEFAULT 'CHAT' NOT NULL,
    `api_key` VARCHAR(255),
    PRIMARY KEY (`lang_model_id`),
    FOREIGN KEY (`project_id`) REFERENCES `tb_project`(`project_id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 7. tb_model_preset (언어 모델 사전 설정 정보)
DROP TABLE IF EXISTS `tb_model_preset`;
CREATE TABLE IF NOT EXISTS `tb_model_preset` (
                                                 `model_preset_id` INT NOT NULL AUTO_INCREMENT,
                                                 `lang_model_id` INT NOT NULL,
                                                 `temperature` FLOAT DEFAULT 1.0 NOT NULL,
                                                 `top_p` FLOAT DEFAULT 1.0 NOT NULL,
                                                 `top_k` INT DEFAULT 1 NOT NULL,
                                                 PRIMARY KEY (`model_preset_id`),
    FOREIGN KEY (`lang_model_id`) REFERENCES `tb_lang_model`(`lang_model_id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 8. tb_conf_knowledge (지식 베이스 설정 정보)
DROP TABLE IF EXISTS `tb_conf_knowledge`;
CREATE TABLE IF NOT EXISTS `tb_conf_knowledge` (
                                                   `conf_knowledge_id` INT NOT NULL AUTO_INCREMENT,
                                                   `project_id` INT NOT NULL,
                                                   `chk_token_size` VARCHAR(255) NOT NULL,
    `overlap_token_rate` FLOAT NOT NULL,
    `emb_model_name` VARCHAR(255) NOT NULL,
    `rerk_model_name` VARCHAR(255) NOT NULL,
    `rerk_top_n` INT DEFAULT 3 NOT NULL,
    `retv_theshold_score` FLOAT DEFAULT 80 NOT NULL,
    `retv_top_k` INT DEFAULT 50 NOT NULL,
    `keyword_weight` FLOAT DEFAULT 0 NOT NULL,
    PRIMARY KEY (`conf_knowledge_id`),
    FOREIGN KEY (`project_id`) REFERENCES `tb_project`(`project_id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 11. tb_conversation (질의응답을 진행한 대화 세션)
DROP TABLE IF EXISTS `tb_conversation`;
CREATE TABLE IF NOT EXISTS `tb_conversation` (
                                                 `conversation_id` INT NOT NULL AUTO_INCREMENT,
                                                 `project_id` INT NOT NULL,
                                                 `title` VARCHAR(50),
    `create_dt` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `used_dt` DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`conversation_id`),
    FOREIGN KEY (`project_id`) REFERENCES `tb_project`(`project_id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 12. tb_question (질의)
DROP TABLE IF EXISTS `tb_question`;
CREATE TABLE IF NOT EXISTS `tb_question` (
                                             `question_id` INT NOT NULL AUTO_INCREMENT,
                                             `conversation_id` INT NOT NULL,
                                             `message` VARCHAR(255),
    `create_dt` DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`question_id`),
    FOREIGN KEY (`conversation_id`) REFERENCES `tb_conversation`(`conversation_id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 13. tb_answer (응답)
DROP TABLE IF EXISTS `tb_answer`;
CREATE TABLE IF NOT EXISTS `tb_answer` (
                                           `answer_id` INT NOT NULL AUTO_INCREMENT,
                                           `question_id` INT NOT NULL,
                                           `message` TEXT,
                                           `pipeline_log` TEXT,
                                           `create_dt` DATETIME DEFAULT CURRENT_TIMESTAMP,
                                           `done_dt` DATETIME,
                                           PRIMARY KEY (`answer_id`),
    FOREIGN KEY (`question_id`) REFERENCES `tb_question`(`question_id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 14. tb_feedback (질의응답 피드백)
DROP TABLE IF EXISTS `tb_feedback`;
CREATE TABLE IF NOT EXISTS `tb_feedback` (
                                             `feedback_id` INT NOT NULL AUTO_INCREMENT,
                                             `answer_id` INT NOT NULL,
                                             `feedback_type` ENUM('GOOD','BAD','INCORRECT','IRRELEVANT','INCOMPLETE','ERROR','ETC'),
    `comment` VARCHAR(255),
    `create_dt` DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`feedback_id`),
    FOREIGN KEY (`answer_id`) REFERENCES `tb_answer`(`answer_id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 15. tb_doc (참조 문서)
DROP TABLE IF EXISTS `tb_doc`;
CREATE TABLE IF NOT EXISTS `tb_doc` (
                                        `doc_id` INT NOT NULL AUTO_INCREMENT,
                                        `project_folder_id` INT NOT NULL,
                                        `name` VARCHAR(50) NOT NULL,
    `page_count` INT,
    `state` ENUM('PENDING','SERVING','INACTIVE') DEFAULT 'PENDING',
    `url` VARCHAR(255) NOT NULL,
    `revision` VARCHAR(25),
    `upload_dt` DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`doc_id`),
    FOREIGN KEY (`project_folder_id`) REFERENCES `tb_project_folder`(`project_folder_id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 16. tb_doc_account (참조 문서에 접근 가능한 계정의 참조 테이블)
DROP TABLE IF EXISTS `tb_doc_account`;
CREATE TABLE IF NOT EXISTS `tb_doc_account` (
                                                `doc_account_id` INT NOT NULL AUTO_INCREMENT,
                                                `doc_id` INT NOT NULL,
                                                `account_id` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`doc_account_id`),
    FOREIGN KEY (`doc_id`) REFERENCES `tb_doc`(`doc_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`account_id`) REFERENCES `tb_account`(`account_id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 17. tb_doc_attr (문서에서 의미있는 내용의 가장 작은 범위의 값)
DROP TABLE IF EXISTS `tb_doc_attr`;
CREATE TABLE IF NOT EXISTS `tb_doc_attr` (
                                             `doc_attr_id` INT NOT NULL AUTO_INCREMENT,
                                             `doc_id` INT NOT NULL,
                                             `name` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`doc_attr_id`),
    FOREIGN KEY (`doc_id`) REFERENCES `tb_doc`(`doc_id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 18. tb_doc_attr_hierarchy (문서의 attr 의 계층 구조)
DROP TABLE IF EXISTS `tb_doc_attr_hierarchy`;
CREATE TABLE IF NOT EXISTS `tb_doc_attr_hierarchy` (
                                                       `ancestor_id` INT NOT NULL,
                                                       `descendant_id` INT NOT NULL,
                                                       `depth` INT NOT NULL,
                                                       PRIMARY KEY (`ancestor_id`, `descendant_id`),
    FOREIGN KEY (`ancestor_id`) REFERENCES `tb_doc_attr`(`doc_attr_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`descendant_id`) REFERENCES `tb_doc_attr`(`doc_attr_id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 19. tb_doc_node (문서의 노드)
DROP TABLE IF EXISTS `tb_doc_node`;
CREATE TABLE IF NOT EXISTS `tb_doc_node` (
                                             `doc_node_id` INT NOT NULL AUTO_INCREMENT,
                                             `doc_attr_id` INT NOT NULL,
                                             PRIMARY KEY (`doc_node_id`),
    FOREIGN KEY (`doc_attr_id`) REFERENCES `tb_doc_attr`(`doc_attr_id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 20. tb_doc_hierarchy (문서의 계층 구조)
DROP TABLE IF EXISTS `tb_doc_hierarchy`;
CREATE TABLE IF NOT EXISTS `tb_doc_hierarchy` (
                                                  `from_node_id` INT NOT NULL,
                                                  `to_node_id` INT NOT NULL,
                                                  `edge` VARCHAR(255),
    `weight` INT NOT NULL,
    PRIMARY KEY (`from_node_id`, `to_node_id`),
    FOREIGN KEY (`from_node_id`) REFERENCES `tb_doc_node`(`doc_node_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`to_node_id`) REFERENCES `tb_doc_node`(`doc_node_id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 21. tb_similarity_doc (질문에서 찾은 유사도가 높은 참조 문서)
DROP TABLE IF EXISTS `tb_similarity_doc`;
CREATE TABLE IF NOT EXISTS `tb_similarity_doc` (
                                                   `similarity_doc_id` INT NOT NULL AUTO_INCREMENT,
                                                   `answer_id` INT NOT NULL,
                                                   `doc_id` INT NOT NULL,
                                                   `page` INT NOT NULL,
                                                   `score` FLOAT NOT NULL,
                                                   PRIMARY KEY (`similarity_doc_id`),
    FOREIGN KEY (`answer_id`) REFERENCES `tb_answer`(`answer_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`doc_id`) REFERENCES `tb_doc`(`doc_id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 22. tb_doc_part (하나의 문서를 세분화한 내용)
DROP TABLE IF EXISTS `tb_doc_part`;
CREATE TABLE IF NOT EXISTS `tb_doc_part` (
                                             `doc_part_id` INT NOT NULL AUTO_INCREMENT,
                                             `doc_id` INT NOT NULL,
                                             `title` VARCHAR(100),
    `chapter_type` ENUM('CONTENTS','CHAPTER'),
    `start_page` INT,
    `end_page` INT,
    `label` VARCHAR(255),
    PRIMARY KEY (`doc_part_id`),
    FOREIGN KEY (`doc_id`) REFERENCES `tb_doc`(`doc_id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 23. tb_doc_image (문서에 포함된 이미지 페이지)
DROP TABLE IF EXISTS `tb_doc_image`;
CREATE TABLE IF NOT EXISTS `tb_doc_image` (
                                              `doc_image_id` INT NOT NULL AUTO_INCREMENT,
                                              `doc_id` INT NOT NULL,
                                              `page` INT NOT NULL,
                                              PRIMARY KEY (`doc_image_id`),
    FOREIGN KEY (`doc_id`) REFERENCES `tb_doc`(`doc_id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 24. tb_doc_keyword (문서에서 발견된 키워드)
DROP TABLE IF EXISTS `tb_doc_keyword`;
CREATE TABLE IF NOT EXISTS `tb_doc_keyword` (
                                                `doc_keyword_id` INT NOT NULL AUTO_INCREMENT,
                                                `doc_id` INT NOT NULL,
                                                `keyword` VARCHAR(20) NOT NULL,
    `count` INT NOT NULL,
    PRIMARY KEY (`doc_keyword_id`),
    FOREIGN KEY (`doc_id`) REFERENCES `tb_doc`(`doc_id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- tb_authority: 권한 정보 (Authority 엔티티)
DROP TABLE IF EXISTS `tb_authority`;
CREATE TABLE IF NOT EXISTS `tb_authority` (
    `authority_name` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`authority_name`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- tb_user: 사용자 정보 (User 엔티티)
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE IF NOT EXISTS `tb_user` (
                                         `user_id` BIGINT NOT NULL AUTO_INCREMENT,
                                         `username` VARCHAR(50) NOT NULL,
    `password` VARCHAR(100) NOT NULL,
    `license_key` VARCHAR(100),
    `activated` BOOLEAN NOT NULL,
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `uk_username` (`username`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- user_authority: 사용자와 권한의 다대다 관계를 위한 조인 테이블
DROP TABLE IF EXISTS `user_authority`;
CREATE TABLE IF NOT EXISTS `user_authority` (
                                                `user_id` BIGINT NOT NULL,
                                                `authority_name` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`user_id`, `authority_name`),
    FOREIGN KEY (`user_id`) REFERENCES `tb_user`(`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`authority_name`) REFERENCES `tb_authority`(`authority_name`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
