package com.idt.aio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "tb_answer")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Answer {

    @Id
    @Column(name = "answer_id")
    private int answerId;

    @Column(name = "question_id")
    private int questionId;

    @Column(name = "message")
    private String message;

    @Column(name = "pipeline_log")
    private String pipelineLog;

    @Column(name = "create_dt")
    private Date createDt;

    @Column(name = "done_dt")
    private Date doneDt;

}
