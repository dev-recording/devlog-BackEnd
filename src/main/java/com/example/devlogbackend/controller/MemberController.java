/*
package com.example.devlogbackend.controller;

import com.example.devlogbackend.service.PrincipalDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MemberController {
    @GetMapping("/member")
    public ResponseEntity<Map<String, Object>> getMember(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        Map<String, Object> memberInfo = new HashMap<>();

        Map<String, Object> attributes = principalDetails.getAttributes();
        memberInfo.put("nickname", attributes.get("login"));
        memberInfo.put("profileImg", attributes.get("avatar_url"));
        memberInfo.put("username", attributes.get("name"));
        memberInfo.put("email", attributes.get("email"));

        return ResponseEntity.ok(memberInfo);
    }
}*/