package com.example.mecanica.os_mecanica.repository;

import com.example.mecanica.os_mecanica.model.Funcionario;
import com.example.mecanica.os_mecanica.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    //Método para buscar todos os veículo
    List<Veiculo> findAll();

    // Método para buscar veículo por ID
    Optional<Veiculo> findById(Long id);
}