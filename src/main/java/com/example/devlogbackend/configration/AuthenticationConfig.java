package com.example.devlogbackend.configration;


import com.example.devlogbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;

//시큐리티를 만듬으로써 인증을 만들어준거임
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthenticationConfig /*extends WebSecurityConfigurerAdapter */{


    private final UserService userService;
    @Value("${jwt.secret}")
    private String secretKey;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity)throws Exception{
        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors().and()
                .authorizeHttpRequests()
                .antMatchers("/login","/join").permitAll()
                .antMatchers(HttpMethod.POST,"login").authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtFilter(userService, secretKey), UsernamePasswordAuthenticationFilter.class)
                .build();
    /*@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 생성 비활성화
                .and()
                .csrf().disable() // CSRF 보안 비활성화
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers(HttpMethod.POST, "/authenticated-endpoint").authenticated()
                .anyRequest().permitAll(); // 다른 요청은 모두 허용*/
    }
}



