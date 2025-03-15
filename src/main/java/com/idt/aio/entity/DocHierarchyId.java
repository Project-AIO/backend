package com.idt.aio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
