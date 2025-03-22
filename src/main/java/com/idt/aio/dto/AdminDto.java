package com.idt.aio.dto;

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
public class AdminDto {

    @NotNull
    @JsonProperty(value = "adminId")
    private String adminId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    //@NotNull
    @Size(min = 3, max = 100)
    private String pw;

    @JsonProperty(value = "licenseKey")
    @Size(min = 3, max = 100)
    private String licenseKey;

    @Builder
    public static AdminDto from (Admin admin) {
        if(admin == null) return null;

        return AdminDto.builder()
                .adminId(admin.getAdminId())
                .pw(admin.getPw())
                .licenseKey(admin.getLicenseKey())
                .build();
    }

}
