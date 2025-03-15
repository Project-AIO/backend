package com.idt.aio.controller;

import com.idt.aio.dto.FeedbackDto;
import com.idt.aio.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class FeedbackController {
    private final FeedbackService feedbackService;
    @Operation(summary = "피드백을 프로젝트 ID 기준으로 가져오는 API", description = """
           프로젝트 ID로 피드백 가져오기
        """)
    @GetMapping("/feedback")
    public List<FeedbackDto> getFeedbackByProjectId(@RequestParam("project_id") final Integer project_id) {

        return feedbackService.fetchFeedbacksByProjectId(project_id);
    }
}
