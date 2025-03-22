package com.idt.aio.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RefreshTokenDto {

    @NotNull
    @JsonProperty(value = "adminId")
    @Size(min = 3, max = 50)
    private String adminId;

    @Size(min = 3, max = 100)
    private String pw;

    @JsonProperty(value = "newAccessToken")
    private String newAccessToken;

    @JsonProperty(value = "newRefreshToken")
    private String newRefreshToken;

}
