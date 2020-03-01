package com.webber.jogging.configuration;

import javax.sql.DataSource;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.webber.jogging.security.CookieClearingLogoutSuccessHandler;
import com.webber.jogging.security.UserResourcePermissionEvaluator;


/*
 * Created: 29.03.2015 10:24:34
 * 
 * @author John
 */

@Configuration
@Profile("production")
@EnableWebSecurity
public class ProductionSecurityConfig extends WebSecurityConfigurerAdapter {

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
    return new CookieClearingLogoutSuccessHandler("https://www.johnlwebber.com/jogging/login.html");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
    .authorizeRequests()
        .antMatchers("/js/**", "/css/**", "/images/**", "/**").permitAll()
//        .antMatchers("/**").hasRole("USER")
        .anyRequest().authenticated()
        .and()
    .formLogin()
        .loginPage("https://www.johnlwebber.com/jogging/login.html")
        .loginProcessingUrl("/login")
        .failureUrl("https://www.johnlwebber.com/jogging/login.html=error=true")
        .defaultSuccessUrl("https://www.johnlwebber.com/jogging/", true)
        .permitAll() 
        .and()
    .logout()
        .logoutUrl("https://www.johnlwebber.com/jogging/logout")
        .permitAll()
        .invalidateHttpSession(true)
        .logoutSuccessHandler(getLogoutSuccessHandler())
        .and()
     .portMapper()
        .http(8080).mapsTo(8443)
        .http(80).mapsTo(443)
        .and()
    .rememberMe()
        .and()
    .csrf()
        .disable();
  }
}
