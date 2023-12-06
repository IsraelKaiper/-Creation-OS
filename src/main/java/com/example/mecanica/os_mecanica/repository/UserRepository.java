package com.example.mecanica.os_mecanica.repository;

import com.example.mecanica.os_mecanica.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;

@Controller

public interface UserRepository extends JpaRepository<Login, Long> {
    Login findByEmailAndSenha(String email, String senha);

    Login findByEmail(String email);
}