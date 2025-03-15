package com.idt.aio.util;

import com.idt.aio.controller.AuthController;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncryptUtil {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final String key = "123456789ABCDEF!";
    private final String algorithm = "AES/CBC/PKCS5Padding";

    public String aesEncode(String str) throws Exception {
        IvParameterSpec iv = new IvParameterSpec(key.substring(0, 16).getBytes());
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
        byte[] encrypted = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(encrypted);
    }


    public String aesDecode(String str) throws Exception {
        IvParameterSpec iv = new IvParameterSpec(key.substring(0, 16).getBytes());

        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(str));

        return new String(decrypted, StandardCharsets.UTF_8);
    }

    /*
        라이선스키 유효기간 설정 (생성일에 Term 일수 플러스)
     */
    public String expireDate(int Term) {
        String expireDate = "";
        LocalDate now = LocalDate.now().plusDays(Term);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        expireDate = now.format(formatter);

        return expireDate;
    }

    /*
        암호화키 생성
        성명|만료기간
     */
    public String genLicenseKey(String username) throws Exception {
        String expireDate = expireDate(180);  //180일
        String encryptKeyStr = "";

        encryptKeyStr = username + "|" + expireDate;
        logger.debug("encryptKeyStr1: {}", encryptKeyStr);
        encryptKeyStr = aesEncode(encryptKeyStr);
        logger.debug("encryptKeyStr2: {}", encryptKeyStr);
        logger.debug("encryptKeyStr3: {}", aesDecode(encryptKeyStr));

        return encryptKeyStr;
    }

}
