package com.example.devlogbackend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data

public class UserDTO {
    private int hashcode;

    private String name;

    private String email;

    private int id;

    private String comment;
}
