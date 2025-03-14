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
@Table(name = "tb_answer")
public class Answer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long answerId;

    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "message")
    private String message;

    @Column(name = "pipeline_log")
    private String pipelineLog;

    @Column(name = "done_dt", updatable = false)
    private LocalDateTime doneDt;

    @Override
    protected void prePersistChild() {
        this.doneDt = LocalDateTime.now();
    }
}
