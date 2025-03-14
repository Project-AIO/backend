package com.idt.aio.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "tb_admin")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Admin {

    @Id
    @Column(name = "admin_id")
    private String admin_id;

    @Column(name = "pw", length = 30)
    private String pw;

    @Column(name = "license_key", length = 100)
    private String licenseKey;

}