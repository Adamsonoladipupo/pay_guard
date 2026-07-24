package com.pay_guard.pay_guard_bkd.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
public class EndpointPrinter {
    @Bean
    CommandLineRunner printEndpoints(
            RequestMappingHandlerMapping mapping
    ) {

        return args -> {

            System.out.println("\n========== REGISTERED ENDPOINTS ==========");

            mapping.getHandlerMethods().forEach((key, value) ->

                    System.out.println(key + " ---> " + value)

            );

            System.out.println("==========================================\n");

        };

    }
}
