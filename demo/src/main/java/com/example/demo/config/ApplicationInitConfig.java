package com.example.demo.config;

import java.util.HashSet;


import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.entity.User;
import com.example.demo.enums.RoleEnum;
import com.example.demo.reponsitory.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

@Configuration
@RequiredArgsConstructor
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;
    @Bean
    ApplicationRunner applicationRunner (UserRepository userRepository){
        return args -> {
            if(userRepository.findByEmail("admin@gmail.com").isEmpty()){
                var roles = new HashSet<String>();
                roles.add(RoleEnum.ADMIN.name());
                User user = User.builder()
                .name("Admin")
                .email("admin@gmail.com")
                .password(passwordEncoder.encode("admin123"))
                .roles(roles)
                .build();
                userRepository.save(user);
                log.warn("Admin user created");
            }

        };
    }
    
}
