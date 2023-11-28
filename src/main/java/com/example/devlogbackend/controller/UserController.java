package com.example.devlogbackend.controller;

import com.example.devlogbackend.dto.UserDTO;
import com.example.devlogbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
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
        public ResponseEntity<String> login(){
           return ResponseEntity.ok().body("token");
        }
        }