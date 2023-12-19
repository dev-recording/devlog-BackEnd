package com.example.devlogbackend.service;

import com.example.devlogbackend.dto.GithubDTO;
import com.nimbusds.oauth2.sdk.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service

public class GitHubOAuthService {

    @Value("${spring.security.oauth2.client.registration.github.client-id}") // application.properties 또는 application.yml에 설정된 값
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.github.client-secret}") // application.properties 또는 application.yml에 설정된 값
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.github.redirect-uri}") // application.properties 또는 application.yml에 설정된 값
    private String redirectUri;

    private static final RestTemplate restTemplate = new RestTemplate();

    public String generateGitHubLoginUrl() {
        // GitHub 로그인 URL 생성
        String authUrl = "https://github.com/login/oauth/authorize";
        return authUrl + "?client_id=" + clientId + "&redirect_uri=" + redirectUri;

    }

    public String getAccessToken(String code) {
        String tokenUrl = "https://github.com/login/oauth/access_token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", code);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, request, String.class);
        String responseBody = response.getBody();

        // access_token을 추출
        String accessToken = null;
        String[] parts = responseBody.split("&");
        for (String part : parts) {
            if (part.startsWith("access_token=")) {
                accessToken = part.substring("access_token=".length());
                break;
            }
        }
        return accessToken;
    }


    public static GithubDTO getUserInfo(String accessToken) {
        // GitHub로부터 사용자 정보를 가져오는 메서드

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://api.github.com").pathSegment("user");
        String apiUrl = builder.toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken); // 엑세스 토큰을 헤더에 추가

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<GithubDTO> response = restTemplate.exchange(apiUrl, HttpMethod.GET, request, GithubDTO.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("GitHub API 호출 실패: " + response.getStatusCode());
        }
    }

    // 사용자 정보를 가져오는 메서드를 추가할 수 있음

}
