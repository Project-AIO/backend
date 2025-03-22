package com.idt.aio.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.idt.aio.entity.Admin;
import lombok.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginDto {

    @NotNull
    @JsonProperty(value = "adminId")
    @Size(min = 3, max = 50)
    private String adminId;

    @NotNull
    @Size(min = 3, max = 100)
    private String pw;

    @JsonProperty(value = "licenseKey")
    @Size(min = 3, max = 100)
    private String licenseKey;

    @JsonProperty(value = "accessToken")
    private String accessToken;

    @JsonProperty(value = "refreshToken")
    private String refreshToken;

    @Builder
    public static LoginDto from (Admin admin, TokenDto tokenDto) {
        if (admin == null) {
            return null;
        }

        return LoginDto.builder()
                .adminId(admin.getAdminId())
                .pw(admin.getPw())
                .licenseKey(admin.getLicenseKey())
                .accessToken(tokenDto.getAccessToken())
                .refreshToken(tokenDto.getRefreshToken())
                .build();
    }

}