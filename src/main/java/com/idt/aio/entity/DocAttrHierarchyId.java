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
public class DocAttrHierarchyId implements Serializable {
    @Column(name = "ancestor_id", nullable = false)
    private Integer ancestorId;

    @Column(name = "descendant_id", nullable = false)
    private Integer descendantId;
}
