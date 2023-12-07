package com.example.mecanica.os_mecanica.repository;

import com.example.mecanica.os_mecanica.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Método para buscar todos os clientes
    List<Cliente> findAll();

    // Método para buscar cliente por nome
    Optional<Cliente> findByNome(String nome);
}