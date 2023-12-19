package com.example.devlogbackend.configration;

import com.example.devlogbackend.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = extractToken(request);
        if (token != null) {
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(jwtUtil.getSecretKey()) // 시크릿 키를 가져옴
                        .parseClaimsJws(token)
                        .getBody();

                String email = claims.get("email", String.class);

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
