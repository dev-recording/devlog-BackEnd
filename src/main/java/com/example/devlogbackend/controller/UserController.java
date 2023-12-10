package com.example.devlogbackend.controller;

import com.example.devlogbackend.dto.MailDTO;
import com.example.devlogbackend.dto.UserDTO;
import com.example.devlogbackend.service.EmailService;
import com.example.devlogbackend.service.UserService;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.Cookie;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private  final EmailService emailService;


    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

       @GetMapping("/join")
        public UserDTO home(@PathVariable int insertUser){

            logger.debug("Received id: {}",insertUser);

          UserDTO dto = userService.getUserJoin(insertUser);
            System.out.println("안녕");
            return dto ;

        }

        @PostMapping("/join")
        public UserDTO save(@RequestBody UserDTO userDTO) {
            // 랜덤한 hashcode 생성
            int randomHashcode = generateRandomHashcode();
            userDTO.setHashcode(randomHashcode);
            // UserService를 통해 데이터베이스에 저장
            return userService.save(userDTO);
        }

        // 랜덤한 hashcode 생성하는 메서드
        private int generateRandomHashcode() {
            Random random = new Random();
            return random.nextInt(); // 랜덤 정수 생성
       }


        @GetMapping("/api/v1/users")
        public UserDTO login(@PathVariable String email){
            UserDTO dto = userService.selectUserlogin(email);
            System.out.println("안녕");
            return dto ;
        }
        @PostMapping("/login")
        public ResponseEntity<Integer> login(@RequestBody UserDTO userDTO, HttpServletResponse response ){
            userService.login(userDTO.getEmail());
            String token = userService.login(userDTO.getEmail());

          Cookie cookie = new Cookie("authToken",token);
           cookie.setPath("/"); // 쿠키의 경로 설정

           // 쿠키를 HttpServletResponse에 추가합니다.
            response.addCookie(cookie);

           return ResponseEntity.ok().body(userDTO.getId());//id

        }

/*
  @GetMapping("/login/github")
    public String githubLogin() {
        return "http://localhost:8080/login/oauth2/code/github";
    }

    @GetMapping("/login/github/callback")
    public String githubCallback() {
        return "redirect:/"; // 로그인 후 리디렉션할 경로
    }

*/




        @PostMapping("/send-email")
        public MailDTO sendEmail(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {

            String code = generateRandomCode();

            String subject = "이메일 인증";
            String text = "인증 코드: " + code; // 코드 생성 로직은 여기에 추가

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.HOUR, 1);
            Date expiredTime = calendar.getTime();

            // 실제 이메일 발송
            MailDTO mailDTO = emailService.sendEmail(email, code, expiredTime);

     /*       if (mailDTO.isSuccess()) {
                // 이메일 발송 성공한 경우, 원하는 페이지로 리디렉션
                return "redirect:/signup";
            } else {
                // 이메일 발송 실패한 경우, 에러 메시지를 리디렉션 시킴
                redirectAttributes.addFlashAttribute("error", mailDTO.getErrorMessage());
                return "redirect:/error-page"; // 에러 페이지로 리디렉션 또는 다른 처리를 수행
            }*/


            redirectAttributes.addFlashAttribute("message", "이메일을 전송했습니다. 이메일을 확인하세요.");

            return mailDTO;
        }



        public String generateRandomCode() {

            SecureRandom secureRandom = new SecureRandom();

            byte[] randomBytes = new byte[32];
            secureRandom.nextBytes(randomBytes);
            String code = Base64.getEncoder().encodeToString(randomBytes);
            return code;
        }

    }


