package com.idt.aio.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LicenseDto {

    @NonNull
    private Integer userId;
    @NonNull
    private String username;
    private String licenseKey;

}
