package com.idt.aio;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.idt.aio.util.EncryptUtil;

@SpringBootTest
class AioApplicationTests {

	private EncryptUtil encryptUtil;

	@Test
	public void aesTest() throws Exception {
		String str = "AES 암호화 테스트";

		//System.out.println("암호화 : "+ encryptUtil.aesEncode(str));
		//System.out.println("복호화 : "+ encryptUtil.aesDecode(encryptUtil.aesEncode(str)));
	}

}
