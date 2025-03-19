package com.idt.aio.repository;

import com.idt.aio.entity.Conversation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Integer> {
    List<Conversation> getConversationByProject_ProjectId(final Integer projectId);

    Conversation getConversationByConversationId(final Integer conversationId);

    void deleteConversationsByProject_ProjectId(final Integer projectId);
}
