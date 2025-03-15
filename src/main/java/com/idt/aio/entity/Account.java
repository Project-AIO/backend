package com.idt.aio.entity;

import com.idt.aio.entity.constant.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tb_account")
public class Account {

    @Id
    @Column(name = "account_id")
    private String accountId;


    @JoinColumn(name = "admin_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Admin admin;


    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

}
