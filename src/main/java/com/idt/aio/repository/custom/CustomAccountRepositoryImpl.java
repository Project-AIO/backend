package com.idt.aio.repository.custom;

import static com.idt.aio.entity.QAccount.account;
import static com.idt.aio.entity.QDocumentAccount.documentAccount;
import static com.idt.aio.entity.QProject.project;
import static com.idt.aio.entity.QProjectAccount.projectAccount;

import com.idt.aio.dto.AccountPermissionDto;
import com.idt.aio.dto.QAccountData;
import com.idt.aio.dto.QAccountPermissionDto;
import com.idt.aio.dto.QDocumentAccountDto;
import com.idt.aio.dto.QProjectAccountDto;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomAccountRepositoryImpl implements CustomAccountRepository{
    private final JPAQueryFactory queryFactory;
    @Override
    public Page<AccountPermissionDto> getAccountPermissionPageByProjectId(final Integer projectId, final int page, final int size) {
        List<AccountPermissionDto> results = queryFactory
                .select(new QAccountPermissionDto(
                        new QDocumentAccountDto(
                                documentAccount.docAccountId,
                                documentAccount.document.docId,
                                documentAccount.account.accountId
                        ),
                        new QProjectAccountDto(
                                projectAccount.projectAccountId,
                                projectAccount.project.projectId,
                                projectAccount.account.accountId
                        ),
                        new QAccountData(
                                account.accountId,
                                account.admin.adminId,
                                account.role,
                                account.name
                        )
                ))
                .from(account)
                .join(projectAccount).on(account.accountId.eq(projectAccount.account.accountId))
                .join(project).on(projectAccount.project.projectId.eq(project.projectId))
                .leftJoin(documentAccount).on(account.accountId.eq(documentAccount.account.accountId))
                .where(project.projectId.eq(projectId))
                .limit(size)
                .offset((long) page * size)
                .fetch();


        long total = Optional.ofNullable(
                queryFactory
                        .select(account.count())
                        .from(account)
                        .join(projectAccount).on(account.accountId.eq(projectAccount.account.accountId))
                        .join(project).on(projectAccount.project.projectId.eq(project.projectId))
                        .leftJoin(documentAccount).on(account.accountId.eq(documentAccount.account.accountId))
                        .where(project.projectId.eq(projectId))
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(results, PageRequest.of(page, size), total);
    }
}
