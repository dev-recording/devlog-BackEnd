package com.example.devlogbackend.service;

import com.example.devlogbackend.dto.OauthDTO;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service

public class GitHubOAuthService {

    @Value("${spring.security.oauth2.client.registration.github.client-id}")
    // application.properties 또는 application.yml에 설정된 값
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.github.client-secret}")
    // application.properties 또는 application.yml에 설정된 값
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.github.redirect-uri}")
    // application.properties 또는 application.yml에 설정된 값
    private String redirectUri;

    private static final RestTemplate restTemplate = new RestTemplate();

    public String generateGitHubLoginUrl() {
        // GitHub 로그인 URL 생성
        String authUrl = "https://github.com/login/oauth/authorize";
        return authUrl + "?client_id=" + clientId + "&redirect_uri=" + redirectUri;
    }

    public String getAccessToken(String code) {
        // GitHub으로부터 엑세스 토큰 요청
        String tokenUrl = "https://github.com/login/oauth/access_token";

        tokenUrl += "?client_id=" + clientId; // GitHub 애플리케이션의 클라이언트 ID
        tokenUrl += "&client_secret=" + clientSecret; // GitHub 애플리케이션의 클라이언트 시크릿
        tokenUrl += "&code=" + code; // 권한 부여 코드
        tokenUrl += "&redirect_uri=" + redirectUri; // 리디렉션 URL

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

       ResponseEntity<String> response = restTemplate.exchange(tokenUrl, HttpMethod.GET,null, String.class);
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
    public static OauthDTO getUserInfo(String accessToken) {
        String apiUrl = "https://api.github.com/user"; // GitHub API 사용자 정보 엔드포인트

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<OauthDTO> responseEntity = restTemplate.getForEntity(apiUrl, OauthDTO.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            return null;
        }
    }
}
