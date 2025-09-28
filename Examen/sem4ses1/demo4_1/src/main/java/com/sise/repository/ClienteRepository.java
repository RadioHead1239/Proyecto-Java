package com.sise.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sise.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> 
{


}
