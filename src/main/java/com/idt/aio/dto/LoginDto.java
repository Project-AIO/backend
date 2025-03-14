package com.idt.aio.dto;

import com.idt.aio.entity.User;
import jakarta.validation.Valid;
import lombok.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    @NotNull
    @Size(min = 3, max = 100)
    private String password;

    @Size(min = 3, max = 100)
    private String licenseKey;

    private String accessToken;
    private String refreshToken;

    /*public LoginDto(@Valid LoginDto loginDto) {
        this.username = username;
        this.password = password;
        this.licenseKey = licenseKey;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }*/

    public static LoginDto from(LoginDto loginDto) {
        if(loginDto == null) return null;

        return LoginDto.builder()
                .username(loginDto.getUsername())
                .password(loginDto.getPassword())
                .licenseKey(loginDto.getLicenseKey())
                .accessToken(loginDto.getAccessToken())
                .refreshToken(loginDto.getRefreshToken())
                .build();
    }
}