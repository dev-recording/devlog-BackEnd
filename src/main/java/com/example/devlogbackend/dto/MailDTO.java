package com.example.devlogbackend.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MailDTO {

    private String email;

    private String code;

    private Date expiredTime;


    public void setMessage(String 이메일_발송_성공) {
    }
}
