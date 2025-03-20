package com.idt.aio.repository;

import com.idt.aio.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Integer> {
    List<Conversation> getConversationByProject_ProjectId(final Integer projectId);

    Conversation getConversationByConversationId(final Integer conversationId);

    void deleteConversationsByProject_ProjectId(final Integer projectId);
}
