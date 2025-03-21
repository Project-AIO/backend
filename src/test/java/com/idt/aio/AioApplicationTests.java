package com.idt.aio;

import com.idt.aio.jwt.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.idt.aio.util.EncryptUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AioApplicationTests {

	@Autowired
	TokenProvider tokenProvider;


	private AuthenticationManagerBuilder authenticationManagerBuilder;
	@BeforeEach
	void setUp() {
		// 테스트용 secret, 만료 시간 설정 (초 단위)
		// 아래 예시에서는 만료 시간을 크게 잡아두었습니다.
		this.tokenProvider = new TokenProvider(
				"c2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQtc2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQK",  // jwt.secret
				60L,                      // jwt.access-token-validity-in-seconds
				120L                      // jwt.refresh-token-validity-in-seconds
		);
		// key 초기화
		tokenProvider.afterPropertiesSet();
	}
	@Test
	void testAccessCreateToken() {
		// 1. 가짜 Authentication 생성
		var authorities = Collections.singletonList(new SimpleGrantedAuthority("USER"));
		var userDetails = new User("testUser", "testPassword", authorities);
		var authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), authorities);

		// 2. 토큰 발급
		String accessToken = tokenProvider.accessCreateToken(authentication);
		System.out.println("accessToken = " + accessToken);

		// 3. 토큰이 정상적으로 생성되었는지 확인
		assertNotNull(accessToken);
		assertTrue(tokenProvider.validateToken(accessToken));

		// 4. 토큰에서 Authentication 추출하여 정보 확인
		var authFromToken = tokenProvider.getAuthentication(accessToken);
		assertEquals("testUser", authFromToken.getName());
		assertTrue(
				authFromToken.getAuthorities().stream()
						.anyMatch(grantedAuth -> grantedAuth.getAuthority().equals("ROLE_USER"))
		);
	}

}
