package com.idt.aio.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import jakarta.persistence.*;
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
    @Column(name = "seq_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sqeNo;

    @JsonProperty(value = "adminId")
    @Column(name = "admin_id", length = 50, unique = true)
    private String adminId;

    @Column(name = "pw", length = 100)
    private String pw;

    @Column(name = "license_key", length = 100)
    private String licenseKey;

    @ManyToMany
    @JoinTable(
            name = "tb_admin_authority",
            joinColumns = {@JoinColumn(name = "admin_id", referencedColumnName = "admin_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

}