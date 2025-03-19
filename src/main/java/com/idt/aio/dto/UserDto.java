package com.idt.aio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.idt.aio.entity.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Integer userId;

    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    //@NotNull
    @Size(min = 3, max = 100)
    private String password;

    @Size(min = 3, max = 100)
    private String licenseKey;

    private Set<AuthorityDto> authorityDtoSet;

    public static UserDto from(User user) {
        if (user == null) {
            return null;
        }

        return UserDto.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .licenseKey(user.getLicenseKey())
                .authorityDtoSet(user.getAuthorities().stream()
                        .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
                        .collect(Collectors.toSet()))
                .build();
    }
}
