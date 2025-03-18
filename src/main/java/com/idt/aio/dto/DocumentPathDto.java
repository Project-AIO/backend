package com.idt.aio.dto;

import lombok.*;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class DocumentPathDto {
    private Integer docId;
    private String path;
}
