package com.example.devlogbackend.controller;

import com.example.devlogbackend.dto.OauthDTO;
import com.example.devlogbackend.dto.MailDTO;
import com.example.devlogbackend.dto.UserDTO;

import com.example.devlogbackend.entity.OuthUser;
import com.example.devlogbackend.repository.AuthUserRepository;
import com.example.devlogbackend.service.EmailService;
import com.example.devlogbackend.service.GitHubOAuthService;
import com.example.devlogbackend.service.FacebookOAuthService;
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
    private  final AuthUserRepository authUserRepository;
    private final FacebookOAuthService facebookOAuthService;


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

    @GetMapping ("/github/callback")
    public ResponseEntity<String> handleGitHubCallback(@RequestParam("code") String code){
        String accessToken = gitHubOAuthService.getAccessToken(code);

        // GitHub API를 사용하여 사용자 정보 가져오기
        OauthDTO oauthDTO = GitHubOAuthService.getUserInfo(accessToken);

        // TODO: 사용자 정보를 저장 또는 처리 (예: 데이터베이스에 사용자 정보 저장)
        // GithubDTO를 GithubUser 엔티티로 변환 (필요에 따라)
        OuthUser githubUser = new OuthUser();
        githubUser.setEmail(oauthDTO.getEmail());
        githubUser.setPassword(oauthDTO.getPassword());

        githubUser.setId(1L);
        // 사용자 정보를 데이터베이스에 저장

        try {
            // 사용자 정보를 데이터베이스에 저장
            authUserRepository.save(githubUser);
        } catch (Exception e) {
            // 예외 처리: 데이터베이스 작업 중에 예외가 발생할 수 있으므로 예외 처리를 고려해야 합니다.
            e.printStackTrace(); // 예외 처리 방식을 선택하여 수정해야 합니다.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("데이터베이스 오류");
        }
//        return ResponseEntity.ok("GitHub OAuth2 인증이 완료되었습니다.");

        return ResponseEntity.ok("Received code: " + code);
    }

    @GetMapping("/facebook/login")
    public ResponseEntity<Void> redirectToFacebookLogin(HttpServletResponse response) {
        String facebookLoginUrl = facebookOAuthService.generateFacebookLoginUrl();
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", facebookLoginUrl).build();
    }

    @GetMapping ("/facebook/callback")
    public ResponseEntity<String> handleFacebookCallback(@RequestParam("code") String code){
        String accessToken = facebookOAuthService.getAccessToken(code);

        // Facebook API를 사용하여 사용자 정보 가져오기
        OauthDTO oauthDTO = facebookOAuthService.getUserInfo(accessToken);

        // TODO: 사용자 정보를 저장 또는 처리 (예: 데이터베이스에 사용자 정보 저장)
        // GithubDTO를 GithubUser 엔티티로 변환 (필요에 따라)
        OuthUser facebookUser = new OuthUser();
        facebookUser.setEmail(oauthDTO.getEmail());
        facebookUser.setPassword(oauthDTO.getPassword());

        facebookUser.setId(1L);
        // 사용자 정보를 데이터베이스에 저장

        try {
            // 사용자 정보를 데이터베이스에 저장
            authUserRepository.save(facebookUser);
        } catch (Exception e) {
            // 예외 처리: 데이터베이스 작업 중에 예외가 발생할 수 있으므로 예외 처리를 고려해야 합니다.
            e.printStackTrace(); // 예외 처리 방식을 선택하여 수정해야 합니다.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("데이터베이스 오류");
        }
//        return ResponseEntity.ok("GitHub OAuth2 인증이 완료되었습니다.");

        return ResponseEntity.ok("Received code: " + code);
    }


        @PostMapping("/send-email")
        public ResponseEntity<MailDTO> sendEmail(@RequestParam("email") String email, @RequestParam("service") String service, RedirectAttributes redirectAttributes) {

            String code = generateRandomCode();

            String subject = "이메일 인증";
            String text = "인증 코드: " + code; // 코드 생성 로직은 여기에 추가

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.HOUR, 1);
            Date expiredTime = calendar.getTime();

            MailDTO mailDTO=new MailDTO();

            if ("naver".equals(service)) {
                mailDTO = emailService.sendNaverEmail(email, code, expiredTime);
            } else if ("gmail".equals(service)) {
                mailDTO = emailService.sendGmailEmail(email, code, expiredTime);
            }

            redirectAttributes.addFlashAttribute("message", "이메일을 전송했습니다. 이메일을 확인하세요.");

            return ResponseEntity.ok(mailDTO);

        }

        public String generateRandomCode() {

            SecureRandom secureRandom = new SecureRandom();

            byte[] randomBytes = new byte[32];
            secureRandom.nextBytes(randomBytes);
            String code = Base64.getEncoder().encodeToString(randomBytes);
            return code;
        }

    }


