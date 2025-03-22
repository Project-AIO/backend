package com.idt.aio.dto;

import com.idt.aio.entity.ProjectAccount;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectAccountDto {

    private Integer projectAccountId;
    private Integer projectId;
    private String accountId;

    public static List<ProjectAccountDto> from(final List<ProjectAccount> projectAccount) {
        return projectAccount.stream()
                .map(p -> ProjectAccountDto.builder()
                        .projectAccountId(p.getProjectAccountId())
                        .projectId(p.getProject().getProjectId())
                        .accountId(p.getAccount().getAccountId())
                        .build()
                )
                .toList();
    }

}