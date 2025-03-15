package com.idt.aio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class AioApplication {

	public static void main(String[] args) {
		SpringApplication.run(AioApplication.class, args);
	}

}
