package com.example.devlogbackend.repository;

import com.example.devlogbackend.entity.GithubUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GithubUserRepository extends JpaRepository<GithubUser, Long> {
    Optional<GithubUser> findByEmail(String email);
}
