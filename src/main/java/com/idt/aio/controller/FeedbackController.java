package com.idt.aio.controller;

import com.idt.aio.dto.FeedbackData;
import com.idt.aio.dto.FeedbackDto;
import com.idt.aio.dto.HomonymDto;
import com.idt.aio.request.FeedbackPageRequest;
import com.idt.aio.request.HomonymPageRequest;
import com.idt.aio.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class FeedbackController {
    private final FeedbackService feedbackService;

    @Operation(summary = "피드백을 프로젝트 ID 기준으로 가져오는 API", description = """
               프로젝트 ID로 피드백 가져오기
            """)
    @GetMapping("/feedbacks")
    public List<FeedbackDto> getFeedbackByProjectId(@RequestParam("project_id") final Integer project_id) {

        return feedbackService.fetchFeedbacksByProjectId(project_id);
    }

    @Operation(summary = "프로젝트 귀속 피드백 목록 페이징 조회 API", description = """
              프로젝트 귀속 피드백 목록 페이징 - direction, sortProperty로 정렬 방식 지정
               direction - ASC, DESC
               sortProperty - answer_id, feeback_type, comment, create_dt
            """)
    @GetMapping("/feedbacks/page")
    public Page<FeedbackData> getHomonymByPage(@ModelAttribute @Valid final FeedbackPageRequest request) {

        return feedbackService.fetchFeebackByProjectIdByPage(request.projectId(), request.page(), request.size(),
                request.direction(), request.sortProperty());
    }

    @Operation(summary = "project id에 해당하는 피드백 모두 제거 API", description = """
              project id에 해당하는 피드백 모두 제거
            """)
    @DeleteMapping("/feedbacks")
    public ResponseEntity<?> deleteFeedbacksByProjectId(@RequestParam("project_id") final Integer projectId) {
        feedbackService.deleteFeedbacksByProjectId(projectId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "피드백ID에 해당하는 피드백 제거 API", description = """
               피드백 제거
            """)
    @DeleteMapping("/feedbacks/{feedback_id}")
    public ResponseEntity<?> deleteFeedback(@PathVariable("feedback_id") final Integer feedback_id) {
        feedbackService.deleteFeedback(feedback_id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
