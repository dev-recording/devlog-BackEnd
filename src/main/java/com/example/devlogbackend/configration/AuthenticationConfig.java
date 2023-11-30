package com.example.devlogbackend.configration;

import io.jsonwebtoken.Jwt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class AuthenticationConfig  extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, Jwt jwt, TokenService tokenService) throws Exception {
        Exception {
            http
                    .authorizeRequests()
                    .requestMatchers("/api/members/signup", "/api/members/signin").permitAll()
                    .anyRequest().authenticated()
                    .and().headers()
                    .disable()
                    .httpBasic()
                    .disable()
                    .rememberMe()
                    .disable()
                    .logout()
                    .disable()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandler())
                    .authenticationEntryPoint(authenticationEntryPoint())
                    .and()
                    .addFilterBefore(jwtAuthenticationFilter(jwt, tokenService), UsernamePasswordAuthenticationFilter.class);

            return http.build();


}

    private <__TMP__> __TMP__ accessDeniedHandler() {
    }

    private <__TMP__> __TMP__ accessDeniedHandler() {
    }