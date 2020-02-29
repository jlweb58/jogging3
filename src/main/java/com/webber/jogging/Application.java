package com.webber.jogging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Application {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) throws Exception {

        // Environment variables are default
        System.out.println("SPRING_PROFILES_ACTIVE=" + System.getenv("SPRING_PROFILES_ACTIVE") + " (Environment)");
        System.out.println("JAVA_ENV=" + System.getenv("JAVA_ENV") + " (Environment)");

        // You can overwrite it with your command line arguments
        System.out.println("spring.profiles.active=" + System.getProperty("spring.profiles.active") + " (Command Line)");

        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);

        System.out.println("Application Running!!!");
    }

}
