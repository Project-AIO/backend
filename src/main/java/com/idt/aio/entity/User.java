package com.idt.aio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Integer userId;

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
    public User(Integer userId, String username, String password, String licenseKey, boolean activated) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.licenseKey = licenseKey;
        this.activated = activated;
    }

    public void update(Integer userId, String licenseKey) {
        this.userId = userId;
        this.licenseKey = licenseKey;
    }

    public void UserEntity(Integer userId, String username, String password, String licenseKey, boolean activated) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.licenseKey = licenseKey;
        this.activated = activated;
    }
}