package com.example.devlogbackend.service;

import com.example.devlogbackend.dto.MailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
    public class EmailService {

    private final JavaMailSender emailSender;


    public MailDTO sendEmail(String email, String code, Date expiredTime) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom("did756984@naver.com");
        message.setSubject("이메일 인증");
        message.setText("인증 코드: " + code + " 유효 기간: " + expiredTime);

        MailDTO mailDTO = new MailDTO();
        mailDTO.setEmail(email);
        mailDTO.setCode(code);
        mailDTO.setExpiredTime(expiredTime);

        try {
            emailSender.send(message);
            mailDTO.setMessage("이메일 발송 성공");
        } catch (Exception e) {
            mailDTO.setMessage("이메일 발송 실패: " + e.getMessage());
        }

        return mailDTO;
    }
    }

