package com.idt.aio.service;

import com.idt.aio.entity.Conversation;
import com.idt.aio.entity.LanguageModel;
import com.idt.aio.entity.Project;
import com.idt.aio.entity.constant.Feature;
import com.idt.aio.exception.DomainExceptionCode;
import com.idt.aio.repository.ConversationRepository;
import com.idt.aio.repository.ProjectRepository;
import com.idt.aio.request.ConversationRequest;
import com.idt.aio.request.LanguageModelRequest;
import com.idt.aio.response.ConversationResponse;
import com.idt.aio.response.LanguageModelResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ConversationService {
    private final ConversationRepository conversationRepository;
    private final ProjectRepository projectRepository;
    @Transactional(readOnly = true)
    public List<ConversationResponse> getConversationByProjectId(final Integer projectId) {
        final List<Conversation> conversations = conversationRepository.getConversationByProject_ProjectId(projectId);
        return ConversationResponse.from(conversations);
    }

    @Transactional(readOnly = true)
    public ConversationResponse getConversationById(final Integer conversationId) {
        final Conversation conversation = conversationRepository.getConversationByConversationId(conversationId);
        return ConversationResponse.from(conversation);
    }

    @Transactional
    public void saveConversation(final ConversationRequest params) {
        if(!projectRepository.existsById(params.projectId())) {
            throw DomainExceptionCode.PROJECT_NOT_FOUND.newInstance();
        }

        final Project referenceById = projectRepository.getReferenceById(params.projectId());

        conversationRepository.save(Conversation.builder()
                .project(referenceById)
                .title(params.title())
                .build());
    }

    @Transactional
    public void deleteConversationByProjectId(final Integer projectId) {
        conversationRepository.deleteConversationsByProject_ProjectId(projectId);
    }

    @Transactional
    public void deleteConversationById(final Integer conversationId) {
        conversationRepository.getConversationByConversationId(conversationId);
    }
}
