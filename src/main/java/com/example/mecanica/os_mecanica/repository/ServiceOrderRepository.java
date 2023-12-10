package com.example.mecanica.os_mecanica.repository;

import com.example.mecanica.os_mecanica.model.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {
    //Método para buscar todos os serviços
    List<ServiceOrder> findAll();

    List<ServiceOrder> findByStatus(String status);
}