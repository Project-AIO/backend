package com.idt.aio.controller;

import com.idt.aio.dto.LicenseDto;
import com.idt.aio.dto.LicenseReIssueDto;
import com.idt.aio.dto.LoginDto;
import com.idt.aio.dto.RefreshTokenDto;
import com.idt.aio.dto.TokenDto;
import com.idt.aio.dto.UserDto;
import com.idt.aio.entity.User;
import com.idt.aio.jwt.JwtFilter;
import com.idt.aio.jwt.TokenProvider;
import com.idt.aio.repository.BlackListRepository;
import com.idt.aio.repository.RefreshTokenRepository;
import com.idt.aio.repository.UserRepository;
import com.idt.aio.service.UserService;
import com.idt.aio.util.EncryptUtil;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final EncryptUtil encryptUtil = new EncryptUtil();
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private BlackListRepository blackListRepository;
    @Autowired
    private UserService userService;

    public AuthController(UserRepository userRepository, TokenProvider tokenProvider,
                          AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    /*
        로그인
        Access Token, Refresh Token 발급
     */
    @PostMapping("/v1/login")
    public ResponseEntity<LoginDto> login(@Valid @RequestBody LoginDto loginDto) throws Exception {
        String username = loginDto.getUsername();
        String blackList = blackListRepository.getBlackList(username);

        String jwtAccess = "";
        String jwtRefresh = "";

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        logger.debug("authenticationToken: {} ", authenticationToken);

        /*
            아이디, 패스워드 인증 성공 후
         */
        User user = userService.findByUsername(username);
        String licenseKey = user.getLicenseKey();
        HttpHeaders httpHeaders = new HttpHeaders();

        //loginDto.setUserId(user.getUserId());
        loginDto.setUsername(user.getUsername());
        loginDto.setLicenseKey(user.getLicenseKey());

        //logger.debug("licenseKey: {}, !license(licenseKey): {} ", licenseKey, !license(licenseKey, username));
        if ((licenseKey == null || licenseKey == "") || !license(username, licenseKey)) {
            logger.debug("licenseKey 공백or유효하지않음: {} ", licenseKey);
            httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwtAccess);
            return new ResponseEntity<>(loginDto, httpHeaders, HttpStatus.FORBIDDEN);
        } else {  //license 가 유효
            logger.debug("licenseKey 유효함: {} ", licenseKey);
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            jwtAccess = tokenProvider.accessCreateToken(authentication);
            jwtRefresh = tokenProvider.refreshCreateToken(authentication);

            loginDto.setAccessToken(jwtAccess);
            loginDto.setRefreshToken(jwtRefresh);

            httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwtAccess);
            //블랙리스트에서 삭제
            if (blackList != null) {
                blackListRepository.deleteBlackList(username);
            }
            //리프레시 토큰 맵에 저장
            refreshTokenRepository.saveRefreshToken(username, jwtRefresh);

            /*UserDto UserDto = userService.getMyUserWithAuthorities();
            logger.debug("UserDto.getUsername(): {} ", UserDto.getUsername());*/

            return new ResponseEntity<>(loginDto, httpHeaders, HttpStatus.OK);
        }

    }

    /*
        로그아웃
        Refresh Token 블랙리스트 등록
     */
    @PostMapping("/v1/logout")
    public ResponseEntity<Void> logout(UserDto userDto) {
        String username = userDto.getUsername();
        String refreshToken = refreshTokenRepository.getRefreshToken(username);

        refreshTokenRepository.deleteRefreshToken(username);
        blackListRepository.saveBlackList(username, refreshToken);
        return ResponseEntity.ok().build();
    }

    /*
       패스워드변경
    */
    @PostMapping("/v1/change-pw")
    public ResponseEntity<Void> changePw(UserDto userDto) {
        logger.debug("changePw 호출");
        //To-Do 패스워드변경
        /*
            1. username 입력 파라이터로 받아 토큰에 저장된 username과 비교
            2. 동일하면 패스워드 업데이트 호출
            3. 정상응답
        
         */

        return ResponseEntity.ok().build();
    }

    /*
        Refresh Token 재발급
     */
    @PostMapping("/v1/auth-refresh")
    public ResponseEntity<TokenDto> refresh(@Valid @RequestBody RefreshTokenDto refreshTokenDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(refreshTokenDto.getUsername(), refreshTokenDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        String username = refreshTokenDto.getUsername();
        String savedRefreshToken = refreshTokenRepository.getRefreshToken(username);
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
            refreshTokenRepository.saveRefreshToken(refreshTokenDto.getUsername(), jwtNewRefresh);

            return new ResponseEntity<>(new TokenDto(jwtNewAccess, jwtNewRefresh), httpHeaders, HttpStatus.OK);
        } else {

            refreshTokenRepository.deleteRefreshToken(refreshTokenDto.getUsername());
            return new ResponseEntity<>(new TokenDto(null, null), httpHeaders, HttpStatus.OK);
        }

    }

    /*
        라이센스키 유효성 검증
        계정,만료일 체크
     */
    @PostMapping("/v1/license")
    public boolean license(String username, String licenseKey) throws Exception {

        boolean licenseYn = false;
        String decryptKeyStr = "";

        String decryptUsername = "";
        String decryptDate = "";

        decryptKeyStr = encryptUtil.aesDecode(licenseKey);
        //logger.debug("decryptKeyStr: {}", decryptKeyStr);
        String[] decryptKeyArray = decryptKeyStr.split("\\|");
        //logger.debug("decryptKeyArray[0]: {}, decryptKeyArray[1]: {} ", decryptKeyArray[0], decryptKeyArray[1]);
        decryptUsername = decryptKeyArray[0];
        decryptDate = decryptKeyArray[1];

        // 포맷터
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String nowDate = formatter.format(LocalDateTime.now());
        //logger.debug("nowDate: {}", nowDate);
        //logger.debug("decryptDate: {}", decryptDate);
        //계정체크
        if (username.equals(decryptUsername)) {
            //만료일 체크 (만료일 같음: 0, 안지남: 양수, 지남: 음수 )
            if (decryptDate.compareTo(nowDate) >= 0) {
                licenseYn = true;
            } else {
                licenseYn = false;
                throw new RuntimeException(decryptDate + " -> 만료일이 지났습니다.");
            }

        } else {
            licenseYn = false;
            throw new RuntimeException(decryptUsername + " -> 맞지 않습니다.");
        }

        return licenseYn;
    }

    /*
        라이센스키 발급
        라이센스키가 없거나 널이거나 잘못된 경우
     */
    @PostMapping("/v1/license-issue")
    public ResponseEntity<LicenseDto> licenseIssue(@Valid @RequestBody LicenseDto licenseDto) throws Exception {

        Integer userId = licenseDto.getUserId();
        String username = licenseDto.getUsername();
        String licenseKey = licenseDto.getLicenseKey();
        String encryptKeyStr = "";

        HttpHeaders httpHeaders = new HttpHeaders();

        if (licenseKey == null || licenseKey == "") {  //없거나 널인 경우
            encryptKeyStr = encryptUtil.genLicenseKey(username);
            logger.debug("/v1/license-issue encryptKeyStr: {} ", encryptKeyStr);
        }

        if (license(username, licenseKey)) {
            logger.debug("라이센스키 정상");
        } else {
            logger.debug("라이센스키 오류");
        }
        
        /*
            계정 검증
         */
        User user = userService.findByUsername(username);
        Integer dbId = user.getUserId();
        if (!userId.equals(dbId)) {
            logger.debug("userId 검증 실패");
            encryptKeyStr = "";
            return new ResponseEntity<>(new LicenseDto(userId, username, encryptKeyStr), httpHeaders,
                    HttpStatus.FORBIDDEN);
        } else {
            logger.debug("userId 검증 성공");
            /*
                라이센스키 업데이트
             */
            UserDto userDto = new UserDto();
            userDto.setUserId(userId);
            userDto.setUsername(username);
            userDto.setLicenseKey(encryptKeyStr);

            //DB업데이트 호출
            //patchLicenseSave(userDto);

            return new ResponseEntity<>(new LicenseDto(userId, username, encryptKeyStr), httpHeaders, HttpStatus.OK);
        }


    }

    /*
        라이센스키 발급 (DB업데이트)
        계정에 라이센스키를 업데이트
     */
    @PatchMapping("/v1/license-save")
    public ResponseEntity<UserDto> patchLicenseSave(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.updateLicenseIssue(userDto));
    }

    /*
        라이센스키 재발급 (인증토큰 유효함 체크)
     */
    @PostMapping("/v1/license-reissue")
    public ResponseEntity<LicenseReIssueDto> licenseReIssue(@Valid @RequestBody LicenseReIssueDto licenseReIssueDto)
            throws Exception {

        String username = licenseReIssueDto.getUsername();

        User user = userService.findByUsername(username);
        Integer userId = user.getUserId();

        String newLicenseKey = encryptUtil.genLicenseKey(username);

        /*
                라이센스키 업데이트
             */
        UserDto userDto = new UserDto();
        userDto.setUserId(userId);
        userDto.setUsername(username);
        userDto.setLicenseKey(newLicenseKey);

        //DB업데이트 호출
        patchLicenseSave(userDto);

        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<>(new LicenseReIssueDto(username, newLicenseKey), httpHeaders, HttpStatus.OK);
    }

}
