package com.pay_guard.pay_guard_bkd;

import com.pay_guard.pay_guard_bkd.config.FraudProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(FraudProperties.class)
@SpringBootApplication
public class PayGuardBkdApplication {

	public static void main(String[] args) {
		SpringApplication.run(PayGuardBkdApplication.class, args);
	}

}
