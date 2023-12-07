package com.example.mecanica.os_mecanica.repository;

import com.example.mecanica.os_mecanica.model.Peca;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PecaRepository extends JpaRepository<Peca, Long> {
    //Método para buscar todos as peças
    List<Peca> findAll();
    Optional<Peca> findById(Long id);
}
