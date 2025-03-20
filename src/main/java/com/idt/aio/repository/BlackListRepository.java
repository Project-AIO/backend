package com.idt.aio.repository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class BlackListRepository {

    public static Map<String, String> blackListMap = new HashMap<>();

    public void saveBlackList(String username, String refreshToken) {
        blackListMap.put(username, refreshToken);
    }

    public String getBlackList(String username) {
        return blackListMap.get(username);
    }

    public void deleteBlackList(String username) {
        blackListMap.remove(username);
    }
}
