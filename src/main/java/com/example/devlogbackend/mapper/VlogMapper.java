package com.example.devlogbackend.mapper;

import com.example.devlogbackend.dto.VlogDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface VlogMapper {
    @Select("SELECT email,id,name,product FROM login.회원가입 WHERE email = #{email}")
    VlogDTO selectUserDTOById(@Param("email") String email);
}
