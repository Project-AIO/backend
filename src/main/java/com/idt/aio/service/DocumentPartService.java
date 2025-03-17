package com.idt.aio.service;

import com.idt.aio.repository.DocumentPartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DocumentPartService {
    private final DocumentPartRepository documentPartRepository;
}
