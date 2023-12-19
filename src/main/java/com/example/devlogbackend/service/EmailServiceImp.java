package com.example.devlogbackend.service;

import com.example.devlogbackend.dto.MailDTO;
import com.example.devlogbackend.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Date;
import org.springframework.mail.javamail.JavaMailSender;

@Service
@RequiredArgsConstructor
    public class EmailServiceImp {

    private final JavaMailSender emailSender;
    private final UserMapper userMapper;

    public MailDTO sendEmail(String email, String code, Date expiredTime) {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        try {
            helper.setTo("did756984@naver.com");
            helper.setFrom("did756984@naver.com");
            helper.setSubject("이메일 인증");

            String htmlContent = "인증 코드: " + code + " 유효 기간: " + expiredTime
                    + "<br><a href='http://localhost:3000/signup'>여기를 클릭하여 가입 페이지로 이동</a>";

            helper.setText(htmlContent, true); // 두 번째 파라미터를 true로 설정하여 HTML을 지원하도록 합니다.

            emailSender.send(message);

            MailDTO mailDTO = new MailDTO();
            mailDTO.setEmail(email);
            mailDTO.setCode(code);
            mailDTO.setExpiredTime(expiredTime);
            mailDTO.setSuccess(true);
            mailDTO.setMessage("이메일 발송 성공");

            // 데이터베이스에 정보 저장
            int result = userMapper.insertSignupEmail(mailDTO);
            if (result > 0) {
                System.out.println("성공");
                // 데이터베이스 저장에 성공했을 때 추가 작업 수행
                // 예를 들어, 회원 가입 코드 저장 성공 메시지 출력 등
            }

            return mailDTO;
        } catch (Exception e) {
            MailDTO mailDTO = new MailDTO();
            mailDTO.setSuccess(false);
            mailDTO.setErrorMessage("이메일 발송 실패: " + e.getMessage());
            return mailDTO;
        }
    }
}

