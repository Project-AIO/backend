package com.idt.aio.repository.custom;

import com.idt.aio.dto.FeedbackDto;

import java.util.List;


public interface CustomFeedbackRepository {
    List<FeedbackDto> fetchFeedbacksByProjectId(final Integer projectId);
}
