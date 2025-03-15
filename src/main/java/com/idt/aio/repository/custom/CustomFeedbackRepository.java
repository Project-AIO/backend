package com.idt.aio.repository.custom;

import com.idt.aio.dto.FeedbackDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


public interface CustomFeedbackRepository {
    List<FeedbackDto> fetchFeedbacksByProjectId(final Integer projectId);
}
