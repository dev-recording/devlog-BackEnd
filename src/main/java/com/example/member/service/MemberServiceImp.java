package com.example.member.service;

import com.example.common.RedisUtil;
import com.example.member.model.LoginDAO;
import com.example.member.model.UserRole;
import com.example.member.model.UserVO;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MemberServiceImp implements MemberService {

    @Autowired
    LoginDAO loginDAO;

    @Override
    public void signUpUser(UserVO userVO) {
        String password = userVO.getPassword();
        LoginDAO.save(userVO);
    }

    @Override
    public UserVO loginUser(String id, String password) throws Exception {
        UserVO userVO = LoginDAO.findByUsername(id);
        if(userVO == null) throw new Exception("유저가 조회되지 않음");
        if(!userVO.getPassword().equals(password))
            throw new Exception("비밀번호가 틀립니다.");
        return userVO;
    }

    @Override
    public void sendVerificationMail(UserVO userVO) {
        String VERIFICATION_LINK = "http://localhost:8080/user/verify/";
        // if(userVO == null) throw new NotFoundException("유저가 조회되지 않음");
        UUID uuid = UUID.randomUUID();
        // redisUtil과 emailService가 코드 어딘가에 정의되어 있다고 가정합니다.
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.setDataExpire(uuid.toString(), userVO.getUsername(), 60 * 30L);
        emailService.sendMail(userVO.getEmail(), "회원가입 인증메일입니다.", VERIFICATION_LINK + uuid.toString());
    }

    @Override
    public void verifyEmail(String key) {
        RedisUtil redisUtil = new RedisUtil();
        String memberId = redisUtil.getData(key);
        UserVO userVO = LoginDAO.findByUsername(memberId);
        // if(userVO==null) throw new NotFoundException("멤버가 조회되지않음");
        modifyUserRole(userVO,UserRole.ROLE_USER);
        redisUtil.deleteData(key);
    }

    @Override
    public void modifyUserRole(UserVO userVO, UserRole userRole){
        userVO.setRole(userRole);
        LoginDAO.save(userVO);
    }
}