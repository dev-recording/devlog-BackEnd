package com.example.devlogbackend.controller;

import com.example.devlogbackend.dto.UserDTO;
import com.example.devlogbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import java.util.Random;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

       @GetMapping("/user")
        public UserDTO home(@PathVariable int insertUser){

            logger.debug("Received id: {}",insertUser);

          UserDTO dto = userService.getUserDTOById(insertUser);
            System.out.println("안녕");
            return dto ;

        }

        @PostMapping("/user")
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
}
