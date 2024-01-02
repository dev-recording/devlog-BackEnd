package com.example.devlogbackend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import com.example.devlogbackend.dto.OauthDTO;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class FacebookOAuthService {

    @Value("${spring.security.oauth2.client.registration.facebook.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.facebook.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.facebook.redirect-uri}")
    private String redirectUri;

    private static final RestTemplate restTemplate = new RestTemplate();

    public String generateFacebookLoginUrl() {
        // Facebook 로그인 URL 생성
        String authUrl = "https://www.facebook.com/v15.0/dialog/oauth";
        return authUrl + "?client_id=" + clientId + "&redirect_uri=" + redirectUri + "&scope=email";
    }

    public String getAccessToken(String code) {
        // Facebook으로부터 엑세스 토큰 요청
        String tokenUrl = "https://graph.facebook.com/v15.0/oauth/access_token";

        tokenUrl += "?client_id=" + clientId;
        tokenUrl += "&client_secret=" + clientSecret;
        tokenUrl += "&code=" + code;
        tokenUrl += "&redirect_uri=" + redirectUri;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<String> response = restTemplate.exchange(tokenUrl, HttpMethod.GET, null, String.class);
        String responseBody = response.getBody();

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


    public OauthDTO getUserInfo(String accessToken) {
        String apiUrl = "https://graph.facebook.com/v15.0/me?fields=id,email"; // Facebook API 사용자 정보 엔드포인트

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("access_token", accessToken);

        ResponseEntity<OauthDTO> responseEntity = restTemplate.getForEntity(builder.toUriString(), OauthDTO.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            return null;
        }
    }
}

