package com.example.devlogbackend.mapper;

import com.example.devlogbackend.dto.MailDTO;
import com.example.devlogbackend.dto.UserDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface UserMapper {
    @Insert("INSERT INTO signup_email (code, expired_time, email) VALUES (#{code}, #{expiredTime}, #{email})")
    int insertSignupEmail(MailDTO mailDTO);

    @Insert("INSERT INTO study_db.user (name, email, id, comment) VALUES (#{name}, #{email}, #{id}, #{comment})")
    int insertUser(UserDTO userDTO);

    UserDTO insertUserDTOById( int userid);

    @Select("SELECT email, id, name, comment FROM study_db.user WHERE email = #{email}")
    UserDTO selectUserlogin(@Param("email") String email);


}
