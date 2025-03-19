package com.idt.aio.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
        if (loginDto == null) {
            return null;
        }

        return LoginDto.builder()
                .username(loginDto.getUsername())
                .password(loginDto.getPassword())
                .licenseKey(loginDto.getLicenseKey())
                .accessToken(loginDto.getAccessToken())
                .refreshToken(loginDto.getRefreshToken())
                .build();
    }
}