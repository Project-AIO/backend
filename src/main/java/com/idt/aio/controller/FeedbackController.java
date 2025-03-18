package com.idt.aio.controller;

import com.idt.aio.dto.FeedbackDto;
import com.idt.aio.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "피드백ID에 해당하는 피드백 제거 API", description = """
               피드백 제거
            """)
    @DeleteMapping("/feedbacks/{feedback_id}")
    public ResponseEntity<?> deleteFeedback(@PathVariable("feedback_id") final Integer feedback_id) {
        feedbackService.deleteFeedback(feedback_id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
