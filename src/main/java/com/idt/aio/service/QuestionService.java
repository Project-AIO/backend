package com.idt.aio.service;

import com.idt.aio.entity.Conversation;
import com.idt.aio.entity.LanguageModel;
import com.idt.aio.entity.Project;
import com.idt.aio.entity.Question;
import com.idt.aio.entity.constant.Feature;
import com.idt.aio.exception.DomainExceptionCode;
import com.idt.aio.repository.ConversationRepository;
import com.idt.aio.repository.QuestionRepository;
import com.idt.aio.request.QuestionRequest;
import com.idt.aio.response.LanguageModelResponse;
import com.idt.aio.response.QuestionResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QuestionService {
    private final ConversationRepository conversationRepository;
    private final QuestionRepository questionRepository;
    public List<QuestionResponse> getQuestionByConversationId(final Integer conversationId) {
        final List<Question> questions = questionRepository.getQuestionByConversation_ConversationId(conversationId);
        return QuestionResponse.from(questions);
    }

    public QuestionResponse getQuestionById(final Integer questionId) {
        final Question question = questionRepository.getQuestionByQuestionId(questionId);
        return QuestionResponse.from(question);
    }

    public void saveQuestion(final QuestionRequest params) {
        if(!conversationRepository.existsById(params.conversationId())) {
            throw DomainExceptionCode.CONVERSATION_NOT_FOUND.newInstance();
        }

        final Conversation referenceById = conversationRepository.getReferenceById(params.conversationId());

        questionRepository.save(Question.builder()
                .conversation(referenceById)
                .message(params.message())
                .build());

    }

    public void deleteQuestionsByConversationId(final Integer conversationId) {
        questionRepository.deleteQuestionsByConversation_ConversationId(conversationId);
    }

    public void deleteQuestionById(final Integer questionId) {
        questionRepository.deleteById(questionId);
    }
}
