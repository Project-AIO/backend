package com.idt.aio.service;

import com.idt.aio.dto.FeedbackData;
import com.idt.aio.dto.FeedbackDto;
import com.idt.aio.dto.HomonymDto;
import com.idt.aio.entity.Feedback;
import com.idt.aio.entity.Homonym;
import com.idt.aio.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    @Transactional(readOnly = true)
    public List<FeedbackDto> fetchFeedbacksByProjectId(final Integer projectId) {
        return feedbackRepository.fetchFeedbacksByProjectId(projectId);
    }

    @Transactional
    public void deleteFeedback(final Integer feedbackId) {
        feedbackRepository.deleteById(feedbackId);
    }

    public void deleteFeedbacksByProjectId(final Integer projectId) {
        feedbackRepository.deleteFeedbackByAnswer_Question_Conversation_Project_ProjectId(projectId);
    }

    @Transactional(readOnly = true)
    public Page<FeedbackData> fetchFeebackByProjectIdByPage(final Integer projectId, final int page, final int size, final Direction direction,
                                                          final String sortProperty) {
        final Pageable pageable = PageRequest.of(page - 1, size, direction, sortProperty);
        final Page<Feedback> feedbacks = feedbackRepository.findByAnswer_Question_Conversation_Project_ProjectId(
                projectId, pageable);
        return feedbacks.map(FeedbackData::from);
    }
}
