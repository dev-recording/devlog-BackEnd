package com.example.devlogbackend.service;

import com.example.devlogbackend.dto.MailDTO;

import java.util.Date;

public interface EmailService {

    MailDTO sendNaverEmail(String email, String code, Date expiredTime);

    MailDTO sendGmailEmail(String email, String code, Date expiredTime);
}
