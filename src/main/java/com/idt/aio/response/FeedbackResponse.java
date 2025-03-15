package com.idt.aio.response;

import com.idt.aio.entity.constant.FeedbackType;
import lombok.Builder;

@Builder
public record FeedbackResponse(
    Integer questionId,
    String questionMessage,
    Integer answerId,
    String answerMessage,
    FeedbackType feedbackType,
    String feedbackComment,
    String documentName,
    Float similarityDocScore
) {
}
