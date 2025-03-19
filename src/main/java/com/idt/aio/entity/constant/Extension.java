package com.idt.aio.entity.constant;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
