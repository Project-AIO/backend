package com.idt.aio.entity.constant;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public enum Extension {
    PDF("application/pdf");

    private final String value;

    //set에다가 다 담아서 set반환
    public static Set<String> getValues() {
        return Arrays.stream(Extension.values())
                .map(Extension::getValue)
                .collect(Collectors.toSet());
    }
}
