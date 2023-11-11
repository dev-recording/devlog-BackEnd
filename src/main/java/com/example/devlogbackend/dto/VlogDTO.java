package com.example.devlogbackend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class VlogDTO {

    private String email;

    private String id;

    private String name;

    private String product;
}
