package com.idt.aio.repository.custom;

import static com.idt.aio.entity.QAccount.account;
import static com.idt.aio.entity.QAnswer.answer;
import static com.idt.aio.entity.QFeedback.feedback;
import static com.idt.aio.entity.QProject.project;
import static com.idt.aio.entity.QProjectAccount.projectAccount;
import static com.idt.aio.entity.QQuestion.question;
import static com.idt.aio.entity.QSimilarityDoc.similarityDoc;
import static com.idt.aio.entity.QDocument.document;
import static com.idt.aio.entity.QConversation.conversation;

import com.idt.aio.dto.FeedbackDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomFeedbackRepositoryImpl implements CustomFeedbackRepository{
    private final JPAQueryFactory queryFactory;
    private final EntityManager em;
    @Override
    public List<FeedbackDto> fetchFeedbacksByProjectId(Integer projectId) {

        return queryFactory.select(
                        Projections.constructor(FeedbackDto.class,
                                projectAccount.account.accountId,
                                question.questionId,
                                question.message,
                                answer.answerId,
                                answer.message,
                                feedback.feedbackType,
                                feedback.comment,
                                document.name,
                                similarityDoc.score
                        )
                ).
                from(projectAccount)
                        .leftJoin(project).on(project.projectId.eq(projectAccount.project.projectId))
                        .leftJoin(conversation).on(project.projectId.eq(conversation.project.projectId))
                        .leftJoin(question).on(conversation.conversationId.eq(question.conversation.conversationId))
                        .leftJoin(answer).on(question.questionId.eq(answer.question.questionId))
                        .leftJoin(feedback).on(answer.answerId.eq(feedback.answer.answerId))
                        .leftJoin(similarityDoc).on(answer.answerId.eq(similarityDoc.answer.answerId))
                        .leftJoin(document).on(similarityDoc.document.docId.eq(document.docId))
                        .leftJoin(account).on(account.accountId.eq(projectAccount.account.accountId))
                .where(project.projectId.eq(projectId))
                .fetch();

    }
}
