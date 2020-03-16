package com.thejacket.springbootH2Mockmvc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@ComponentScan("com.thejacket.springbootH2Mockmvc")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private ExtendedBasicAuthenticationEntryPoint authenticationEntryPoint;

    /* Configure users along with in-memory credentials for HTTP Basic Authentication */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user1").password(passwordEncoder().encode("user1Pass")).authorities("ROLE_USER")
                .and()
                .withUser("manager1").password(passwordEncoder().encode("manager1Pass")).authorities("ROLE_USER", "ROLE_ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /* Configure permissions for access to API endpoints */
        http.authorizeRequests()
                .antMatchers("/mission/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.GET, "/missions/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/product/*").access("hasRole('ROLE_USER')")
                .antMatchers("/products").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/products/report/*").access("hasRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.POST,"/orders").access("hasRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.GET,"/order/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.GET,"/orders").access("hasRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.GET,"/orders/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().authenticated()
                .and()
                .httpBasic().realmName("TEST REALM")
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint);

        /* Disabling for development*/
        http.csrf().disable();
        http.cors().disable();
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        /* Allowing access to h2 database by web browsers */
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}