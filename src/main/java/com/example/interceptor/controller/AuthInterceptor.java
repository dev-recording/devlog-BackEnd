package com.example.interceptor.controller;

import com.example.member.model.UserVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 세션에서 로그인 정보를 확인하거나 다른 로그인 체크 로직을 수행
        UserVO loggedInUser = (UserVO) request.getSession().getAttribute("loggedInUser");

        if (loggedInUser == null) {
            // 로그인되지 않은 경우 로그인 페이지로 리다이렉트 또는 에러 처리
            response.sendRedirect("/login");
            return false;
        }

        return true; // 로그인 상태인 경우 컨트롤러 메서드 호출 허용
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 컨트롤러 메서드 호출 후에 수행할 작업
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 뷰 렌더링 완료 후에 수행할 작업
    }
}
