package com.idt.aio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LicenseDto {

    @NonNull
    private Integer userId;
    @NonNull
    private String username;
    private String licenseKey;

}
