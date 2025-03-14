package com.idt.aio.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LicenseReIssueDto {

    @NonNull
    private String username;

    @NonNull
    private String licenseKey;

}
