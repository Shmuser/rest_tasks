package com.template.spring_server.injection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@ComponentScan(lazyInit = true)
public class AppConfig {

    @Bean
    @Scope("singleton")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
