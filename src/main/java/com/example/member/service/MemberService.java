package com.example.member.service;

import com.example.member.model.UserRole;
import com.example.member.model.UserVO;

public interface MemberService {
    void signUpUser(UserVO userVO);

    UserVO loginUser(String id, String password) throws Exception;

    public void sendVerificationMail(UserVO userVO);

    public void verifyEmail(String key);

    public void modifyUserRole(UserVO userVO, UserRole userRole);

}