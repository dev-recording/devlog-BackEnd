package com.example.devlogbackend.service;

import com.example.devlogbackend.dto.UserDTO;
import com.example.devlogbackend.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserDTO getUserJoin(int insertUser){
        System.out.println("service:"+insertUser);

        logger.debug("getUserDTOById 호출됨");

        return userMapper.insertUserDTOById(insertUser);
    }

    public UserDTO save(UserDTO userDTO) {


        try {
            int rowsInserted = userMapper.insertUser(userDTO);

            if (rowsInserted > 0) {
                logger.debug("사용자 정보가 성공적으로 저장되었습니다.");
            } else {
                logger.error("사용자 정보 저장 실패");
                // 예외 처리 또는 오류 메시지 반환
                throw new DataIntegrityViolationException("중복된 ID가 발생했습니다.");
            }
        } catch (DataIntegrityViolationException e) {
            logger.error("사용자 정보 저장 중 오류 발생: " + e.getMessage());
            // 예외 처리 또는 오류 메시지 반환
        }

        // 저장된 사용자 정보를 반환
        return userDTO;
    }

    public UserDTO selectUserlogin(String email ) {
        return userMapper.selectUserlogin(email);
    }
}



