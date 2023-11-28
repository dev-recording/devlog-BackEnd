package com.example.member.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Setter
@Getter
@ToString
@Entity
@Table(name = "user")
public class UserVO {
        @Id
        @GeneratedValue
        private int seq;

        @Column(unique = true)
        @NotBlank
        private String username;
        @NotBlank
        private String password;
        @NotBlank
        private String name;
        @NotBlank
        private String email;
        @NotBlank
        private String address;

        @Column(name = "role")
        @Enumerated(EnumType.STRING)
        private UserRole role = UserRole.ROLE_NOT_PERMITTED;

        @Temporal(TemporalType.TIMESTAMP)
        @CreationTimestamp
        private Date createAt;
        @Temporal(TemporalType.TIMESTAMP)
        @UpdateTimestamp
        private Date updateAt;

}
