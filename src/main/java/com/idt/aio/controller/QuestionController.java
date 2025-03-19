package com.idt.aio.controller;

import com.idt.aio.request.QuestionRequest;
import com.idt.aio.response.QuestionResponse;
import com.idt.aio.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class QuestionController {
    private final QuestionService questionService;

    @Operation(summary = "대화 ID로 질문 조회 API", description = """
               대화 ID로 질문 조회
            """)
    @GetMapping("/questions")
    public List<QuestionResponse> fetchQuestionByConversationId(
            @RequestParam("conversation_id") final Integer conversationId) {
        return questionService.getQuestionByConversationId(conversationId);
    }

    @Operation(summary = "질문 ID로 질문 조회 API", description = """
               질문 ID로 질문 조회
            """)
    @GetMapping("/question")
    public QuestionResponse fetchQuestionByQuestionId(@RequestParam("question_id") final Integer questionId) {
        return questionService.getQuestionById(questionId);

    }

    @Operation(summary = "특정 대화의 질문 추가 API", description = """
               특정 대화의 질문 추가
            """)
    @PostMapping("/question")
    public ResponseEntity<?> saveQuestion(@ModelAttribute @Valid final QuestionRequest request) {
        questionService.saveQuestion(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "특정 대화의 질문 삭제 API", description = """
               대화ID로 질문 삭제
            """)
    @DeleteMapping("/questions")
    public ResponseEntity<?> deleteQuestionsByConversationId(
            @RequestParam("conversation_id") final Integer conversationId) {
        questionService.deleteQuestionsByConversationId(conversationId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "질문 ID로 질문 삭제 API", description = """
               질문 ID로 질문 삭제
            """)
    @DeleteMapping("/questions/{question_id}")
    public ResponseEntity<?> deleteQuestionById(@PathVariable("question_id") final Integer questionId) {
        questionService.deleteQuestionById(questionId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
