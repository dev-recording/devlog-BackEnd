package com.example.devlogbackend.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "auth_user")
public class OuthUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;

    private String githubAccessToken;
    private String facebookAccessToken;

}

