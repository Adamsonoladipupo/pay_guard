package com.pay_guard.pay_guard_bkd.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    @NotBlank(message = "JWT secret cannot be empty.")
    private String secret;

    @Min(value = 60000, message = "JWT expiration must be at least 60 seconds.")
    private long expiration;
}
