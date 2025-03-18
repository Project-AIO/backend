package com.idt.aio.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LicenseReIssueDto {

    @NonNull
    private String username;

    @NonNull
    private String licenseKey;

}
