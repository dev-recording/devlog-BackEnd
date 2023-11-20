package com.example.devlogbackend.mapper;

import com.example.devlogbackend.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT id,code,email,expired_time FROM login.signup_email WHERE id = #{id}")
    UserDTO selectUserDTOById(@Param("id") String id);
}
