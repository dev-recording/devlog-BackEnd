package com.example.devlogbackend.controller;

import com.example.devlogbackend.dto.UserDTO;
import com.example.devlogbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

        @GetMapping("/user/{id}")
        public UserDTO home(@PathVariable String id){

            logger.debug("Received id: {}",id);

            UserDTO dto = userService.getUserDTOById(id);
            System.out.println("안녕");
            return dto ;

        }

        /*@PostMapping("/user/{id}")
         public UserDTO save(ModelAttribute UserDTO userDTO){
            return userService.save(id);
        }*/
}
