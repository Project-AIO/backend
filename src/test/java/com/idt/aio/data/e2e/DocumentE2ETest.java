package com.idt.aio.data.e2e;


import com.idt.aio.data.domain.ConfigurationKnowledgeFixture;
import com.idt.aio.data.domain.ProjectFixture;
import com.idt.aio.data.domain.ProjectFolderFixture;
import com.idt.aio.entity.ConfigurationKnowledge;
import com.idt.aio.entity.Project;
import com.idt.aio.entity.ProjectFolder;
import com.idt.aio.repository.ConfigurationKnowledgeRepository;
import com.idt.aio.repository.ProjectFolderRepository;
import com.idt.aio.repository.ProjectRepository;
import com.idt.aio.request.RuleData;
import com.idt.aio.service.ProjectService;
import com.idt.aio.token.TokenProviderTest;
import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DocumentE2ETest {
    @LocalServerPort
    int port;
    @Autowired
    TokenProviderTest tokenProviderTest;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ProjectFolderRepository projectFolderRepository;
    @Autowired
    ConfigurationKnowledgeRepository configurationKnowledgeRepository;
    private  final Pattern UUID_PATTERN = Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

    private final static Integer CREATED_PROJECT_ID = 1;
    private final static String CREATED_FILE_NAME = "test_name";
    @Autowired
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        final List<Project> projects = ProjectFixture.createProjects(3);
        final List<ProjectFolder> projectFolders = ProjectFolderFixture.createProjectFolders(3, projects);
        final List<ConfigurationKnowledge> configs =ConfigurationKnowledgeFixture.createConfigurationKnowledges(projects,3);

        projectRepository.saveAll(projects);
        projectFolderRepository.saveAll(projectFolders);
        configurationKnowledgeRepository.saveAll(configs);
    }

    @Test
    public void 지식베이스_만들기_클릭_api가_정상적으로_작동합니다() throws IOException {
        String accessToken = tokenProviderTest.getTestAccessToken();

        File fileData = new File("src/test/java/com/idt/aio/data/file/test_file.pdf");


        RuleData data = RuleData.builder()
                .startPage(1)
                .endPage(3)
                .title("sdf")
                .build();

        List<RuleData> contentData = List.of(data);

        MultiPartSpecification file =
                new MultiPartSpecBuilder(fileData)
                        .fileName(fileData.getName())
                        .controlName("file") // Multipart 영역의 이름
                        .mimeType(MediaType.APPLICATION_PDF_VALUE)
                        .charset("UTF-8") // 여기서 charset 지정...!
                        .build();


        Response response = given()// RequestSpec 사용
                .header("Authorization", "Bearer " + accessToken)
                .contentType("multipart/form-data")
                .multiPart(file)  // PDF 파일 업로드
                .multiPart("project_folder_id", "1", "application/json") // 일반 문자열 파라미터
                .multiPart("project_id", "1", "application/json") // 일반 문자열 파라미터
                .multiPart("file_name", CREATED_FILE_NAME, "application/json") // 일반 문자열 파라미터
                .multiPart("contents", contentData, "application/json") // JSON 데이터
                .when()
                .post("/api/v1/document/upload")
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value()) // 응답 코드 검증
                .extract()
                .response();

        String jobId = response.getBody().asString();

     //   Path filePath = Paths.get(directory, CREATED_FILE_NAME);
        Assertions.assertThat(UUID_PATTERN.matcher(jobId.trim()).matches()).isTrue();

    }
/*    @AfterEach
    void tearDown() {
        projectService.deleteProjectById(CREATED_PROJECT_ID);
    }*/


}
