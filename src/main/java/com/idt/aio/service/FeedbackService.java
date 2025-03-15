package com.idt.aio.service;

import com.idt.aio.dto.FeedbackDto;
import com.idt.aio.repository.FeedbackRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
