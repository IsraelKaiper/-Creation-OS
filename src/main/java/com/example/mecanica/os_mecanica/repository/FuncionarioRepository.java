package com.example.mecanica.os_mecanica.repository;

import com.example.mecanica.os_mecanica.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    //Método para buscar todos os funcionários
    List<Funcionario> findAll();
    Optional<Funcionario> findById(Long id);
}