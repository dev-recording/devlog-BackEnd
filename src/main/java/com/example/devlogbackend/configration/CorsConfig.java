package com.example.devlogbackend.configration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // 허용할 오리진(도메인)을 설정합니다.
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드를 설정합니다.
                .allowCredentials(true) // 자격 증명(쿠키)을 허용합니다.
                .maxAge(3600); // Preflight 요청의 유효 기간을 설정합니다.
    }
}
