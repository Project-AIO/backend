package com.idt.aio.dto;

import com.idt.aio.entity.Feedback;
import com.idt.aio.entity.constant.FeedbackType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record FeedbackData(
        Integer feedbackId,
        Integer answerId,
        FeedbackType feedbackType,
        String comment,
        LocalDateTime createdDt
)   {
    public static FeedbackData from(final Feedback feedback) {
        return FeedbackData.builder()
                .feedbackId(feedback.getFeedbackId())
                .answerId(feedback.getAnswer().getAnswerId())
                .feedbackType(feedback.getFeedbackType())
                .comment(feedback.getComment())
                .createdDt(feedback.getCreateDt())
                .build();
    }
    public static List<FeedbackData> from(List<Feedback> feedbacks) {
        return feedbacks.stream()
                .map(FeedbackData::from)
                .toList();
    }
}
