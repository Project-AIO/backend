package com.idt.aio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class DocumentRelatedIds {
    private Integer projectId;
    private Integer projectFolderId;
    private Integer docId;


}
