package com.idt.aio.entity;


import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import java.time.LocalDateTime;
import lombok.Getter;

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

    protected void prePersistChild() {
    }

}
