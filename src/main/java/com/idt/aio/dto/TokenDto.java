package com.idt.aio.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenDto {

    private String accessToken;
    private String refreshToken;

}
