package com.idt.aio.service;

import com.idt.aio.dto.UserDto;
import com.idt.aio.entity.Authority;
import com.idt.aio.entity.User;
import com.idt.aio.exception.DuplicateMemberException;
import com.idt.aio.exception.NotFoundMemberException;
import com.idt.aio.repository.UserRepository;
import com.idt.aio.util.SecurityUtil;
import java.util.Collections;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserDto signup(UserDto userDto) {
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                //.licenseKey(userDto.getLicenseKey())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return UserDto.from(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public UserDto getUserWithAuthorities(String username) {
        return UserDto.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
    }

    @Transactional(readOnly = true)
    public UserDto getMyUserWithAuthorities() {
        return UserDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userRepository::findOneWithAuthoritiesByUsername)
                        .orElseThrow(() -> new NotFoundMemberException("Member not found"))
        );
    }

    @Transactional
    public UserDto updateLicenseIssue(UserDto userDto) {
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) == null) {
            throw new NotFoundMemberException("가입안된 회원명입니다.");
        }
        /*User user = User.builder()
                .userId(id)
                .username(userDto.getUsername())
                //.password(passwordEncoder.encode(userDto.getPassword()))
                .licenseKey(userDto.getLicenseKey())
                //.authorities(Collections.singleton(authority))
                //.activated(true)
                .build();*/
        User user = userRepository.findByUsername(userDto.getUsername())
                .orElseThrow(() -> new NotFoundMemberException("가입안된 아이디입니다."));
        user.update(user.getUserId(), userDto.getLicenseKey());

        return userDto;
    }

    @Transactional
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
        return user;
    }

}
