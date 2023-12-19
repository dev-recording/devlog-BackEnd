package com.example.devlogbackend.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Data
@Entity
@Table(name = "github")
public class GithubUser {

    @Id
    private Long id;

    private String email;
    private String password;

    // Getter, Setter, 생성자 등 필요한 메서드를 추가합니다.
}
