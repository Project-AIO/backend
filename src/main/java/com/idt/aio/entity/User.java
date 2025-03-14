package com.idt.aio.entity;

import lombok.*;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tb_user")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "username", length = 50, unique = true)
    private String username;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "license_key", length = 100)
    private String licenseKey;

    @Column(name = "activated")
    private boolean activated;

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

    /*public User() {

    }*/

    @Builder
    public User(Long userId, String username, String password, String licenseKey, boolean activated) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.licenseKey = licenseKey;
        this.activated = activated;
    }

    public void update(Long userId, String licenseKey) {
        this.userId = userId;
        this.licenseKey = licenseKey;
    }

    public void UserEntity(Long userId, String username, String password, String licenseKey, boolean activated) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.licenseKey = licenseKey;
        this.activated = activated;
    }
}