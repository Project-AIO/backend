package com.idt.aio.service;

import java.util.Collections;

import com.idt.aio.dto.AdminDto;
import com.idt.aio.entity.Admin;
import com.idt.aio.entity.Authority;
import com.idt.aio.exception.DuplicateMemberException;
import com.idt.aio.exception.NotFoundMemberException;
import com.idt.aio.repository.AdminRepository;
import com.idt.aio.util.SecurityUtil;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public AdminDto createUser(AdminDto AdminDto) {
        if (adminRepository.findOneWithAuthoritiesByAdminId(AdminDto.getAdminId()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        Admin admin = Admin.builder()
                .adminId (AdminDto.getAdminId())
                .pw(passwordEncoder.encode(AdminDto.getPw()))
                //.licenseKey(AdminDto.getLicenseKey())
                .authorities(Collections.singleton(authority))
                //.activated(true)
                .build();

        return AdminDto.from(adminRepository.save(admin));
    }

    @Transactional(readOnly = true)
    public AdminDto getUserWithAuthorities(String username) {
        return AdminDto.from(adminRepository.findOneWithAuthoritiesByAdminId(username).orElse(null));
    }

    @Transactional(readOnly = true)
    public AdminDto getMyUserWithAuthorities() {
        return AdminDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(adminRepository::findOneWithAuthoritiesByAdminId)
                        .orElseThrow(() -> new NotFoundMemberException("Member not found"))
        );
    }

    @Transactional
    public AdminDto saveLicenseKey(AdminDto AdminDto) {

        String adminId = AdminDto.getAdminId();

        if (adminRepository.findByAdminId(adminId).orElse(null) == null) {
            throw new NotFoundMemberException("가입안된 회원명입니다.");
        }

        Admin admin = adminRepository.findByAdminId(adminId).orElseThrow(() -> new UsernameNotFoundException(adminId + " -> 데이터베이스에서 찾을 수 없습니다."));

        admin.setAdminId(AdminDto.getAdminId());
        admin.setLicenseKey(AdminDto.getLicenseKey());
        adminRepository.save(admin);

        return AdminDto;
    }

    @Transactional
    public Admin findByAdminId(String username) {
        Admin admin = adminRepository.findByAdminId(username).orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
        return admin;
    }

}
