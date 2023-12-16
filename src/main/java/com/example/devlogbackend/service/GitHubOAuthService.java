package com.example.devlogbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GitHubOAuthService {

    @Value("${spring.security.oauth2.client.registration.github.client-id}") // application.properties 또는 application.yml에 설정된 값
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.github.client-secret}") // application.properties 또는 application.yml에 설정된 값
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.github.redirect-uri}") // application.properties 또는 application.yml에 설정된 값
    private String redirectUri;

    private final RestTemplate restTemplate = new RestTemplate();

    public String generateGitHubLoginUrl() {
        // GitHub 로그인 URL 생성
        String authUrl = "https://github.com/login/oauth/authorize";
        return authUrl + "?client_id=" + clientId + "&redirect_uri=" + redirectUri;
    }

    public String getAccessToken(String code) {
        // GitHub으로부터 엑세스 토큰 요청
        String tokenUrl = "https://github.com/login/oauth/access_token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(
                "code=" + code + "&client_id=" + clientId + "&client_secret=" + clientSecret,
                headers);

        ResponseEntity<String> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, request, String.class);
        String responseBody = response.getBody();

        // 엑세스 토큰을 파싱
        String accessToken = null;
        if (responseBody != null && responseBody.contains("access_token")) {
            String[] parts = responseBody.split("&");
            for (String part : parts) {
                if (part.startsWith("access_token=")) {
                    accessToken = part.substring("access_token=".length());
                    break;
                }
            }
        }

        return accessToken;
    }

    // 사용자 정보를 가져오는 메서드를 추가할 수 있음
}
