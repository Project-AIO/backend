package com.idt.aio.token;

import com.idt.aio.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class TokenProviderTest {
    @Autowired
    TokenProvider tokenProvider;
    @Value("${jwt.secret}")
    String secret;

    public String getTestAccessToken(){
        this.tokenProvider = new TokenProvider(
                secret,  // jwt.secret
                600L,                      // jwt.access-token-validity-in-seconds
                600L                      // jwt.refresh-token-validity-in-seconds
        );
        // key 초기화
        tokenProvider.afterPropertiesSet();

        final List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("USER"));

        final User userDetails = new User("testUser", "testPassword", authorities);
        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), authorities);


        // 2. 토큰 발급
        return tokenProvider.accessCreateToken(authentication);
    }
}
