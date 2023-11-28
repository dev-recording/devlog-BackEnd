package com.example.member.model;

import org.springframework.data.repository.CrudRepository;

public interface LoginDAO extends CrudRepository<UserVO, Long> {
    static UserVO findByUsername(String username) {
        return null;
    }

}