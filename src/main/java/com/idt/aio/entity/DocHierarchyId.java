package com.idt.aio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Embeddable
public class DocHierarchyId implements Serializable {
    @Column(name = "from_node_id", nullable = false)
    private Integer fromNodeId;

    @Column(name = "to_node_id", nullable = false)
    private Integer toNodeId;
}
