package com.idt.aio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_conversation")
public class Conversation extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conversation_id")
    private Long conversationId;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "title", length = 50)
    private String title;

    @Column(name = "used_dt", updatable = false)
    private LocalDateTime usedDt;

    @Override
    protected void prePersistChild() {
        this.usedDt = LocalDateTime.now();
    }


}
