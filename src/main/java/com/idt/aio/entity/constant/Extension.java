package com.idt.aio.entity.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum Extension {

    PDF("pdf");

    private final String value;

    //값을 다 set에 담아서 반환
    public static Set<String> getValues() {
        return Arrays.stream(Extension.values())
                .map(Extension::getValue)
                .collect(Collectors.toSet());
    }
}
