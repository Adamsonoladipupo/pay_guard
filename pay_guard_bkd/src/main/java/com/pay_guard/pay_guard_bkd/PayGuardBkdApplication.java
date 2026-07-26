package com.pay_guard.pay_guard_bkd;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@ConfigurationPropertiesScan
@EnableJpaAuditing
@SpringBootApplication
public class PayGuardBkdApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PayGuardBkdApplication.class, args);
	}

	@Override
	public void run(String... args) {
		System.out.println("JWT_SECRET = " + System.getenv("JWT_SECRET"));
		System.out.println("DATABASE_URL = " + System.getenv("DATABASE_URL"));
	}

}
