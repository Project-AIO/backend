package com.idt.aio.dto;

import com.idt.aio.util.StringListConverter;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.Convert;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
