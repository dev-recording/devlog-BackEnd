package com.example.devlogbackend.configration;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

//시큐리티를 만듬으로써 인증을 만들어준거임
@Configuration
@EnableWebSecurity
public class AuthenticationConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity)throws Exception{
        return httpSecurity.httpBasic().disable()
                .csrf().disable().cors().and()
                .authorizeHttpRequests()
                .dispatcherTypeMatchers("/login","/join").permitAll()
                .dispatcherTypeMatchers(HttpMethod.POST, DispatcherType.valueOf("/login")).authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .build();

    }
}