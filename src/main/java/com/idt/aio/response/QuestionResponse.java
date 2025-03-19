package com.idt.aio.response;

import com.idt.aio.entity.Question;
import java.util.List;
import lombok.Builder;

@Builder
public record QuestionResponse(
        Integer questionId,
        Integer conversationId,
        String message
) {
    public static QuestionResponse from(final Question question) {
        return QuestionResponse.builder()
                .questionId(question.getQuestionId())
                .conversationId(question.getConversation().getConversationId())
                .message(question.getMessage())
                .build();
    }

    public static List<QuestionResponse> from(final List<Question> questions) {
        return questions.stream()
                .map(QuestionResponse::from)
                .toList();
    }
}
