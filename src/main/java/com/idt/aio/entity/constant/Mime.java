package com.idt.aio.entity.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public enum Mime {
    PDF("application/pdf");

    private final String value;

    //set에다가 다 담아서 set반환
    public static Set<String> getValues() {
        return Arrays.stream(Mime.values())
                .map(Mime::getValue)
                .collect(Collectors.toSet());
    }
}
