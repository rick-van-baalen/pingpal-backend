package com.pingpal.pingpal_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserDetailsConfig {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails devUser = User.withUsername("dev_admin")
                .password(passwordEncoder.encode("V9k!2x#PzB$eTm4W"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(devUser);
    }
    
}