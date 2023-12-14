package com.example.devlogbackend.controller;

import com.example.devlogbackend.dto.MailDTO;
import com.example.devlogbackend.dto.UserDTO;
import com.example.devlogbackend.service.EmailService;
import com.example.devlogbackend.service.GitHubOAuthService;
import com.example.devlogbackend.service.UserService;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.Cookie;

import org.springframework.http.HttpStatus;
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
    private final GitHubOAuthService gitHubOAuthService;

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

    @GetMapping("/github/login")
    public ResponseEntity<Void> redirectToGitHubLogin(HttpServletResponse response) {
        String githubLoginUrl = gitHubOAuthService.generateGitHubLoginUrl();
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", githubLoginUrl).build();
    }
    @GetMapping("/github/callback")
    public ResponseEntity<String> handleGitHubCallback(@RequestParam("code") String code) {
        String accessToken = gitHubOAuthService.getAccessToken(code);
        // GitHub으로부터 받은 엑세스 토큰을 저장하거나 처리
        // 예: 사용자 정보를 가져오고 저장하거나 세션에 저장
        return ResponseEntity.ok("GitHub OAuth2 인증이 완료되었습니다.");
    }

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


