package com.example.mecanica.os_mecanica.repository;

import com.example.mecanica.os_mecanica.model.Equipe;
import com.example.mecanica.os_mecanica.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EquipeRepository  extends JpaRepository<Equipe, Long> {
    //Método para buscar todos os veículo
    List<Equipe> findAll();

    // Método para buscar veículo por ID
    Optional<Equipe> findById(Long id);
}
