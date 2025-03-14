package com.idt.aio.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LicenseDto {

    @NonNull
    private Long userId;
    @NonNull
    private String username;
    private String licenseKey;

}
