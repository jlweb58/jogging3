package com.webber.jogging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

@SpringBootApplication
public class Application {

    // Fix the CORS errors

    @Bean
    public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // *** URL below needs to match the Vue client URL and port ***
        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
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
