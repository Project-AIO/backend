package com.idt.aio.controller;

import com.idt.aio.dto.FeedbackDto;
import com.idt.aio.dto.SynonymDto;
import com.idt.aio.request.SynonymRequest;
import com.idt.aio.service.SynonymService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class SynonymController {
    private final SynonymService synonymService;
    @Operation(summary = "프로젝트 귀속 동의어 사전 목록 조회 API", description = """
           
        """)
    @GetMapping("/synonym")
    public Page<SynonymDto> getSynonymByPage(@ModelAttribute final SynonymRequest request) {

        return synonymService.fetchSynonymsByProjectIdByPage(request.projectId(), request.page(), request.size());
    }

}
