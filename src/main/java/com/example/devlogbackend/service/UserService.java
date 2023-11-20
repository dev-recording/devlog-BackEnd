package com.example.devlogbackend.service;

import com.example.devlogbackend.dto.UserDTO;
import com.example.devlogbackend.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserDTO getUserDTOById(String id){
        System.out.println("service:"+id);

        logger.debug("getUserDTOById 호출됨");

        return userMapper.selectUserDTOById(id);
    }

}


