package com.idt.aio.controller;


import com.idt.aio.service.FileService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TestController {
    private final FileService fileService;
    @GetMapping("/test")
    public void test() throws IOException {
        fileService.createFolder("test");
    }
}
