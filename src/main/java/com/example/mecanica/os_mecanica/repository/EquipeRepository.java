package com.example.mecanica.os_mecanica.repository;

import com.example.mecanica.os_mecanica.model.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EquipeRepository extends JpaRepository<Equipe, Long> {
    // Método para buscar equipe por ID
    Optional<Equipe> findById(Long id);
}