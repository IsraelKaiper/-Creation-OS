package com.example.mecanica.os_mecanica.repository;

import com.example.mecanica.os_mecanica.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    //Método para buscar todos os funcionários
    List<Veiculo> findAll();
}