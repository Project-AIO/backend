package com.idt.aio.controller;

import com.idt.aio.dto.*;
import com.idt.aio.entity.Admin;
import com.idt.aio.jwt.JwtFilter;
import com.idt.aio.jwt.TokenProvider;
import com.idt.aio.repository.BlackListRepository;
import com.idt.aio.repository.RefreshTokenRepository;
import com.idt.aio.service.AdminService;
import com.idt.aio.util.EncryptUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequestMapping("/api")
public class AdminController {

    //private final AdminRepository userRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final EncryptUtil encryptUtil = new EncryptUtil();

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private BlackListRepository blackListRepository;
    @Autowired
    private final AdminService adminService;

    public AdminController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, AdminService adminService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.adminService = adminService;
    }

    @Operation(summary = "회원가입 API", description = """
               신규 회원가입
            """)
    @PostMapping("/v1/signup")
    public ResponseEntity<AdminDto> createUser(@Valid @RequestBody AdminDto AdminDto) {
        return ResponseEntity.ok(adminService.createUser(AdminDto));
    }

    @Operation(summary = "로그인 API", description = """
               로그인 처리
            """)
    @PostMapping("/v1/signin")
    public ResponseEntity<LoginDto> signInUser(@Valid @RequestBody LoginDto loginDto) throws Exception {
        String adminId = loginDto.getAdminId();
        String pw = loginDto.getPw();
        //String blackList = blackListRepository.getBlackList(adminId);

        String jwtAccess = "";
        String jwtRefresh = "";

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(adminId, pw);
        log.debug("authenticationToken: {} ", authenticationToken);

        /*
            아이디, 패스워드 인증 성공 후
         */
        Admin admin = adminService.findByAdminId(adminId);
        String licenseKey = admin.getLicenseKey();
        loginDto.setLicenseKey(admin.getLicenseKey());

        HttpHeaders httpHeaders = new HttpHeaders();

        //logger.debug("licenseKey: {}, !license(licenseKey): {} ", licenseKey, !license(licenseKey, username));
        if (!licenseValidation(adminId, licenseKey)) {
            log.debug("licenseKey 공백or유효하지않음: {} ", licenseKey);
            httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwtAccess);
            return new ResponseEntity<>(loginDto, httpHeaders, HttpStatus.FORBIDDEN);
        } else {  //license 가 유효
            log.debug("licenseKey 유효함: {} ", licenseKey);
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            jwtAccess = tokenProvider.accessCreateToken(authentication);
            jwtRefresh = tokenProvider.refreshCreateToken(authentication);

            //TokenDto tokenDto = new TokenDto();
            loginDto.setAccessToken(jwtAccess);
            loginDto.setRefreshToken(jwtRefresh);

            httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwtAccess);
            //블랙리스트에서 삭제
            /*if (blackList != null) {
                blackListRepository.deleteBlackList(adminId);
            }*/
            //리프레시 토큰 맵에 저장
            refreshTokenRepository.saveRefreshToken(adminId, jwtRefresh);

            //AdminDto AdminDto = adminService.getMyUserWithAuthorities();
            //log.debug("AdminDto.getUsername(): {} ", AdminDto.getAdminId());

            return new ResponseEntity<>(loginDto, httpHeaders, HttpStatus.OK);
        }

    }

    @Operation(summary = "로그아웃 API", description = """
               로그아웃 처리
            """)
    @PostMapping("/v1/signout")
    public ResponseEntity<Void> signOutUser(AdminDto AdminDto) {
        String adminId = AdminDto.getAdminId();
        String refreshToken = refreshTokenRepository.getRefreshToken(adminId);

        refreshTokenRepository.deleteRefreshToken(adminId);
        blackListRepository.saveBlackList(adminId, refreshToken);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "관리자 패스워드 변경 API", description = """
               관리자의 패스워드를 변경
            """)
    @PostMapping("/v1/change-pw")
    public ResponseEntity<Void> changePw(AdminDto AdminDto) {
        log.debug("changePw 호출");
        //To-Do 패스워드변경
        /*
            1. username 입력 파라이터로 받아 토큰에 저장된 username과 비교
            2. 동일하면 패스워드 업데이트 호출
            3. 정상응답

         */

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Refresh Token 발급 API", description = """
               Refresh Token 재발급 처리
            """)
    @PostMapping("/v1/auth-refresh")
    public ResponseEntity<TokenDto> refresh(@Valid @RequestBody RefreshTokenDto refreshTokenDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(refreshTokenDto.getAdminId(), refreshTokenDto.getPw());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        String adminId = refreshTokenDto.getAdminId();
        String savedRefreshToken = refreshTokenRepository.getRefreshToken(adminId);
        /*리프레시 토큰 유효성 검증 (로그아웃이나비밀번호 변경시 리프레시 토큰 폐기) -> 리프레시 토큰 메모리(DB)삭제
        리프레시 토큰 정상이면 AccessToken, RefreshToken 생성 후 클라이언트로 전송 -> 리프레시 토큰 메모리(DB)저장
        리프레시 토큰이 비정상이면 인증오류
        리프레시 토큰이 만료면 인증불가 -> 메모리(DB)삭제
         */
        HttpHeaders httpHeaders = new HttpHeaders();
        if (savedRefreshToken != null && savedRefreshToken.equals(
                refreshTokenDto.getNewRefreshToken())) {  //리프레시 토큰이 유효

            String jwtNewAccess = tokenProvider.accessCreateToken(authentication);
            String jwtNewRefresh = tokenProvider.refreshCreateToken(authentication);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwtNewAccess);
            //리프레시 토큰 맵에 저장
            refreshTokenRepository.saveRefreshToken(refreshTokenDto.getAdminId(), jwtNewRefresh);

            return new ResponseEntity<>(new TokenDto(jwtNewAccess, jwtNewRefresh), httpHeaders, HttpStatus.OK);
        } else {

            refreshTokenRepository.deleteRefreshToken(refreshTokenDto.getAdminId());
            return new ResponseEntity<>(new TokenDto(null, null), httpHeaders, HttpStatus.OK);
        }

    }

    @Operation(summary = "License Key 유효성 검증 API", description = """
               License Key 의 로그인 계정여부, 만료일 유효성을 검증
            """)
    @PostMapping("/v1/license")
    public boolean licenseValidation(String adminId, String licenseKey) throws Exception {

        boolean licenseYn = false;
        String decryptKeyStr = "";

        String decryptAdminId = "";
        String decryptDate = "";

        /*if (licenseKey == null || licenseKey == "") {
            licenseYn = false;
            throw new RuntimeException("LicenseKey 공백(or Null)입니다.");
        }*/

        decryptKeyStr = encryptUtil.aesDecode(licenseKey);
        String[] decryptKeyArray = decryptKeyStr.split("\\|");
        //logger.debug("decryptKeyArray[0]: {}, decryptKeyArray[1]: {} ", decryptKeyArray[0], decryptKeyArray[1]);
        decryptAdminId = decryptKeyArray[0];
        decryptDate = decryptKeyArray[1];

        // 포맷터
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String nowDate = formatter.format(LocalDateTime.now());
        //logger.debug("nowDate: {}", nowDate);
        //logger.debug("decryptDate: {}", decryptDate);
        //계정체크
        if (adminId.equals(decryptAdminId)) {
            //만료일 체크 (만료일 같음: 0, 안지남: 양수, 지남: 음수 )
            if (decryptDate.compareTo(nowDate) >= 0) {
                licenseYn = true;
            } else {
                licenseYn = false;
                throw new RuntimeException(decryptDate + " -> 만료일이 지났습니다.");
            }

        } else {
            licenseYn = false;
            throw new RuntimeException(decryptAdminId + " -> 맞지 않습니다.");
        }

        return licenseYn;
    }

    @Operation(summary = "License Key 발급 API", description = """
               License Key 생성
            """)
    @PostMapping("/v1/license-issue")
    public ResponseEntity<LicenseDto> licenseIssue(@Valid @RequestBody LicenseDto licenseDto) throws Exception {

        String adminId = licenseDto.getAdminId();
        String licenseKey = licenseDto.getLicenseKey();
        int term = licenseDto.getTerm();
        String encLicenseKey = "";
        HttpHeaders httpHeaders = new HttpHeaders();
        encLicenseKey = encryptUtil.genLicenseKey(adminId, term);

        return new ResponseEntity<>(new LicenseDto(adminId, encLicenseKey, term), httpHeaders, HttpStatus.OK);
    }

    @Operation(summary = "License Key 저장 API", description = """
               License Key 를 저장
            """)
    @PatchMapping("/v1/license-save")
    public ResponseEntity<AdminDto> saveLicenseKey(@Valid @RequestBody AdminDto AdminDto) {
        return ResponseEntity.ok(adminService.saveLicenseKey(AdminDto));
    }

    @Operation(summary = "사용자 조회 API", description = """
               사용자 조회
            """)
    @GetMapping("/v1/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<AdminDto> getMyUserInfo(HttpServletRequest request) {
        return ResponseEntity.ok(adminService.getMyUserWithAuthorities());
    }

    @Operation(summary = "ADMIN권한 보유자의 사용자 조회 API", description = """
               ADMIN권한을 보유자가 사용자 조회
            """)
    @GetMapping("/v1/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<AdminDto> getUserInfo(@PathVariable(value="username") String username) {
        return ResponseEntity.ok(adminService.getUserWithAuthorities(username));
    }
}
