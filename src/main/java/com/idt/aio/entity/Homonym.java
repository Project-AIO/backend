package com.idt.aio.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tb_homonym")
public class Homonym {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "homonym_id")
    private Integer homonymId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project ;


    @Column(name = "source", nullable = false, length = 25)
    private String source;


    @Column(name = "match", nullable = false, length = 25)
    private String match;

}
