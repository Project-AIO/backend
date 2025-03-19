package com.idt.aio.response;

import com.idt.aio.entity.Conversation;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record ConversationResponse(
        Integer conversationId,
        Integer projectId,
        String title,
        LocalDateTime updatedAt,
        LocalDateTime createdAt
) {

    static public ConversationResponse from(Conversation conversation) {
        return ConversationResponse.builder()
                .conversationId(conversation.getConversationId())
                .projectId(conversation.getProject().getProjectId())
                .title(conversation.getTitle())
                .updatedAt(conversation.getUsedDt())
                .createdAt(conversation.getCreateDt())
                .build();
    }

    static public List<ConversationResponse> from(List<Conversation> conversations) {
        return conversations.stream()
                .map(ConversationResponse::from)
                .toList();
    }
}
