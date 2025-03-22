package com.idt.aio.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.idt.aio.entity.Account;
import com.idt.aio.entity.constant.Role;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDto {

    private String accountId;
    private String adminId;
    private Role role;
    private String name;

    private String licenseKey;
    private LocalDate expiredDt;

    public static List<AccountDto> from(List<Account> account) {
        return account.stream()
            .map(a -> AccountDto.builder()
                    .accountId(a.getAccountId())
                    .adminId(a.getAdmin().getAdminId())
                    .name(a.getName())
                    .role(a.getRole())
                    .licenseKey(a.getAdmin().getLicenseKey())
                    .build()
            )
            .toList();
    }

}
