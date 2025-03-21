package com.idt.aio.repository;

import com.idt.aio.entity.Feedback;
import com.idt.aio.entity.Homonym;
import com.idt.aio.repository.custom.CustomFeedbackRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer>, CustomFeedbackRepository {
    void deleteFeedbackByAnswer_Question_Conversation_Project_ProjectId(Integer projectId);

    Page<Feedback> findByAnswer_Question_Conversation_Project_ProjectId(final Integer projectId, final Pageable pageable);
}
