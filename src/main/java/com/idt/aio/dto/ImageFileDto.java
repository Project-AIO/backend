package com.idt.aio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImageFileDto {
    private Resource imageFile;
    private int page;
}
