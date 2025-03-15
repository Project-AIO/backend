package com.idt.aio.dto;

import com.idt.aio.entity.constant.FeedbackType;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class FeedbackDto {
    private String accountId;
    private Integer questionId;
    private String questionMessage;
    private Integer answerId;
    private String answerMessage;
    private FeedbackType feedbackType;
    private String feedbackComment;
    private String documentName;
    private Float similarityDocScore;

    @QueryProjection
    public FeedbackDto(String accountId, Integer questionId, String questionMessage, Integer answerId, String answerMessage, FeedbackType feedbackType, String feedbackComment, String documentName, Float similarityDocScore) {
        this.accountId = accountId;
        this.questionId = questionId;
        this.questionMessage = questionMessage;
        this.answerId = answerId;
        this.answerMessage = answerMessage;
        this.feedbackType = feedbackType;
        this.feedbackComment = feedbackComment;
        this.documentName = documentName;
        this.similarityDocScore = similarityDocScore;
    }

}
