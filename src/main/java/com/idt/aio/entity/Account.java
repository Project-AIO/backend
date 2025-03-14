package com.idt.aio.entity;

import com.idt.aio.util.StringListConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tb_account")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @Column(name = "account_id", length = 100, unique = true)
    private String accountId;

    @Column(name = "admin_id")
    private String adminId;

    @Column(name = "role")
    private String role;

    @Column(name = "name", length = 50, unique = true)
    private String name;

    public void AccountEntity(String accountId, String adminId, String role, String name) {
        this.accountId = accountId;
        this.adminId = adminId;
        this.role = role;
        this.name = name;
    }

}
