package com.idt.aio.controller;

import com.idt.aio.request.ConversationRequest;
import com.idt.aio.response.ConversationResponse;
import com.idt.aio.service.ConversationService;
import io.swagger.v3.oas.annotations.Operation;
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
public class ConversationController {
    private final ConversationService conversationService;
    @Operation(summary = "프로젝트 ID로 대화 조회 API", description = """
               프로젝트 ID로 대화 조회
            """)
    @GetMapping("/conversations")
    public List<ConversationResponse> fetchConversationsByProjectId(@RequestParam("project_id") final Integer projectId) {
        return conversationService.getConversationsByProjectId(projectId);
    }

    @Operation(summary = "대화 ID로 대화 조회 API", description = """
               언어모델 ID로 언어모델 조회
            """)
    @GetMapping("/conversation")
    public ConversationResponse fetchConversationByModelId(@RequestParam("conversation_id") final Integer conversationId) {
        return conversationService.getConversationById(conversationId);

    }

    @Operation(summary = "특정 프로젝트의 대화 추가 API", description = """
               특정 프로젝트의 대화 추가
            """)
    @PostMapping("/conversation")
    public ResponseEntity<?> saveConversation(@ModelAttribute final ConversationRequest request) {
        conversationService.saveConversation(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "특정 프로젝트의 대화 삭제 API", description = """
               projectId로 대화 삭제
            """)
    @DeleteMapping("/conversations")
    public ResponseEntity<?> deleteConversationsByProjectId(@RequestParam("project_id") final Integer projectId) {
        conversationService.deleteConversationsByProjectId(projectId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "대화 ID로 대화 삭제 API", description = """
               대화 ID로 대화 삭제
            """)
    @DeleteMapping("/conversations/{conversation_id}")
    public ResponseEntity<?> deleteConversationById(@PathVariable("conversation_id") final Integer conversationId) {
        conversationService.deleteConversationById(conversationId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
