package com.example.devlogbackend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@Data
public class MailDTO {

    private String email;

    private String code;

    private Date expiredTime;

    private String message;

    private boolean success;

    private String errorMessage;

    public void setMessage(String 이메일_발송_성공) {
    }

}
