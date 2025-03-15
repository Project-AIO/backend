package com.idt.aio.repository;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class RefreshTokenRepository {

    public static Map<String, String> refreshTokenMap = new HashMap<>();

    public void saveRefreshToken(String username, String refreshToken) {
        refreshTokenMap.put(username, refreshToken);
    }

    public String getRefreshToken(String username) {
        return refreshTokenMap.get(username);
    }

    public void deleteRefreshToken(String username) {
        refreshTokenMap.remove(username);
    }
}
