package com.idt.aio.entity;


import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public class BaseEntity {
    @Column(name = "create_dt", updatable = false)
    private LocalDateTime createDt;

    @PrePersist
    public void prePersist() {
        createDt = LocalDateTime.now();
        prePersistChild();
    }
    protected void prePersistChild(){};
}
