package com.idt.aio.repository;

import com.idt.aio.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> getQuestionByConversation_ConversationId(final Integer conversationId);

    Question getQuestionByQuestionId(final Integer questionId);

    void deleteQuestionsByConversation_ConversationId(final Integer conversationId);

}
