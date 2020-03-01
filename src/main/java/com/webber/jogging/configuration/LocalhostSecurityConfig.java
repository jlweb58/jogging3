package com.webber.jogging.configuration;

import com.webber.jogging.security.CookieClearingLogoutSuccessHandler;
import com.webber.jogging.security.UserResourcePermissionEvaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.sql.DataSource;

    @Configuration
    @Profile("localhost")
    @EnableWebSecurity
    public class LocalhostSecurityConfig extends WebSecurityConfigurerAdapter {

        private static final Logger LOG = LoggerFactory.getLogger(LocalhostSecurityConfig.class);

        @Autowired
        private DataSource dataSource;

        @Bean
        public PermissionEvaluator getPermissionEvaluator() {
            return new UserResourcePermissionEvaluator();
        }

        @Bean
        public PasswordEncoder getPasswordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public MethodSecurityExpressionHandler getMethodSecurityExpressionHandler() {
            DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
            expressionHandler.setPermissionEvaluator(getPermissionEvaluator());
            return expressionHandler;
        }

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
            LOG.info("configuring global security for localhost");
            builder
                    .jdbcAuthentication()
                    .dataSource(dataSource)
                    .passwordEncoder(getPasswordEncoder())
                    .authoritiesByUsernameQuery("SELECT u.username, r.rolename " +
                            "FROM users u, user_role r " +
                            "WHERE u.id = r.userid " +
                            "AND u.username=?;")
                    .usersByUsernameQuery(
                            "SELECT username, password, CASE enabled WHEN 1 THEN 'true' ELSE 'false' END 'enabled' FROM users WHERE username=?;");
        }

        @Bean
        public LogoutSuccessHandler getLogoutSuccessHandler() {
            return new CookieClearingLogoutSuccessHandler("/login.html");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            LOG.info("configuring http security for localhost");

            http
                    .authorizeRequests()
                    .antMatchers("/js/**", "/css/**", "/images/**").permitAll()
                    .antMatchers("/**").hasRole("USER")
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login.html")
                    .loginProcessingUrl("/login")
                    .failureUrl("/login.html=error=true")
                    .permitAll()
                    .and()
                    .logout()
                    .logoutUrl("/logout")
                    .permitAll()
                    .invalidateHttpSession(true)
                    .logoutSuccessHandler(getLogoutSuccessHandler())
                    .and()
                    .rememberMe()
                    .and()
                    .csrf()
                    .disable();
        }
}
