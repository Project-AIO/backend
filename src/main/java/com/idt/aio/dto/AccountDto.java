package com.idt.aio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.idt.aio.entity.Account;
import com.idt.aio.entity.User;
import com.idt.aio.util.StringListConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    @NotNull
    private String accountId;

    @NotNull
    private String adminId;

    @NotNull
    private String role;

    @NotNull
    private String name;

    @Convert(converter = StringListConverter.class)
    private List<String> accountDtoSet;

    public AccountDto(String accountId, String adminId, String role, String name) {
        this.accountId = accountId;
        this.adminId = adminId;
        this.role = role;
        this.name = name;
    }

}
