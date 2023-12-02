package com.example.devlogbackend.configration;

import com.example.devlogbackend.dto.UserDTO;

import com.example.devlogbackend.service.UserService;
import com.google.common.net.HttpHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Watchable;
import java.util.List;

//jwt의 이쪾으로 통과할 수 있는 문
//jwt가 매번 있는지 체크 해주는 메서드이고 토큰이 없으면 권한을 부여하지않는 메서드
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {


private final UserService userService;

private final String secretKey;

    //여기서 권한 부여
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        logger.info("authorization : {}"+authorization);

        //token안보내면 null
        if (authorization==null){
            logger.error("authorization 이 없습니다");
            filterChain.doFilter(request,response);
            return;
        }
        //문을 여는 로직
        //UserId에서 토큰 꺼내기
        String userId ="";

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userId, null, List.of(new SimpleGrantedAuthority("root")));

        //디테일 넣기
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);

    }
}
