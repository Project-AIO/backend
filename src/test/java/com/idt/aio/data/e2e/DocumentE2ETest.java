package com.idt.aio.data.e2e;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.idt.aio.token.TokenProviderTest;
import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private RequestSpecification requestSpec;
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
        // ✅ 2. 테스트용 PDF 파일 준비
        File fileData = new File("src/test/java/com/idt/aio/data/file/test_file.pdf");

        // ✅ 3. JSON 데이터 준비
        RuleData data = RuleData.builder()
                .startPage(1)
                .endPage(3)
                .title("sdf")
                .build();

        List<RuleData> contentData = List.of(data);

        requestSpec = new RequestSpecBuilder()
                .setContentType("multipart/form-data") // 명확하게 multipart 설정
                .addHeader("Authorization", "Bearer " + accessToken) // 여기에 토큰 넣기
                .build();

        MultiPartSpecification contents =
                new MultiPartSpecBuilder(contentData, ObjectMapperType.JACKSON_2)
                        .controlName("contents") // Multipart 영역의 이름
                        .mimeType(MediaType.APPLICATION_JSON_VALUE)
                        .charset("UTF-8") // 여기서 charset 지정...!
                        .build();

        MultiPartSpecification file =
                new MultiPartSpecBuilder(fileData, ObjectMapperType.JACKSON_2)
                        .controlName("file") // Multipart 영역의 이름
                        .mimeType(MediaType.APPLICATION_PDF_VALUE)
                        .charset("UTF-8") // 여기서 charset 지정...!
                        .build();


        Response response = given()// RequestSpec 사용
                .header("Authorization", "Bearer " + accessToken)
                .contentType("multipart/form-data")
                .multiPart("file", fileData, "application/pdf")  // PDF 파일 업로드
                .multiPart("project_folder_id", "1", "application/json") // 일반 문자열 파라미터
                .multiPart("project_id", "1", "application/json") // 일반 문자열 파라미터
                .multiPart("file_name", "justlikett", "application/json") // 일반 문자열 파라미터
                .multiPart("contents", contentData, "application/json") // JSON 데이터
                .when()
                .post("/api/v1/document/upload")
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value()) // 응답 코드 검증
                .extract()
                .response();

        String jobId = response.jsonPath().getString("job_id");
        System.out.println("jobId = " + jobId);

    }


}
