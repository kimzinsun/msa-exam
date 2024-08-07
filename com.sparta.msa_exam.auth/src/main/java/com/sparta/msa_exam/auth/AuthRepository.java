package com.sparta.msa_exam.auth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<User, String> {
}
