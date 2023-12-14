package com.example.devlogbackend.configration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final String secretKey = "hello.world.ee.ff"; // JWT를 생성할 때 사용한 시크릿 키

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = extractToken(request);
        if (token != null) {
            try {
                Jws<Claims> claims = Jwts.parser()
                        .setSigningKey(secretKey.getBytes()) // 시크릿 키를 바이트 배열로 전달
                        .parseClaimsJws(token);

                String email = claims.getBody().get("email", String.class);

                // 사용자 정보를 가져와서 인증 처리
                Authentication authentication = buildAuthentication(email);

                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (SignatureException e) {
                // JWT 서명이 유효하지 않은 경우 처리
            }
        }
        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        // HTTP 요청에서 JWT 토큰을 추출하는 로직
        // 예: Authorization 헤더 또는 쿼리 매개변수에서 토큰 추출
        return null;
    }

    private Authentication buildAuthentication(String email) {
        // 사용자 정보를 기반으로 Authentication 객체를 만들어 반환하는 로직
        // 예: UserDetails를 구현한 사용자 정보 클래스를 사용하여 Authentication 객체를 생성
        return null;
    }
}
