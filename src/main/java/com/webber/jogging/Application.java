package com.webber.jogging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

@SpringBootApplication
@EnableScheduling
@EntityScan
public class Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

       public static void main(String[] args) throws Exception {


        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);

           // Environment variables are default
           LOGGER.info("JAVA_ENV=" + System.getenv("JAVA_ENV") + " (Environment)");

           LOGGER.error("log4j security fix active=" + System.getProperty("log4j2.formatMsgNoLookups"));

           // You can overwrite it with your command line arguments
           LOGGER.info("spring.profiles.active=" + System.getProperty("spring.profiles.active") + " (Command Line)");
        LOGGER.info("Application Running!!!");
        //System.out.println(new BCryptPasswordEncoder().encode("pw"));

    }

}
