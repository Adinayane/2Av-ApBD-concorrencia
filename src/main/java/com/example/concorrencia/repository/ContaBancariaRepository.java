package com.example.concorrencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.concorrencia.model.ContaBancaria;

public interface ContaBancariaRepository extends JpaRepository <ContaBancaria, Long> {

}
