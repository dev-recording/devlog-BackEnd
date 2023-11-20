package com.example.devlogbackend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserDTO {


    private String id;

    private String email;

    private String code;

    private String expired_time;
}
