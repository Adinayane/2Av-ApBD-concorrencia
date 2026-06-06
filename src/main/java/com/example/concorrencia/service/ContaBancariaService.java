package com.example.concorrencia.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.concorrencia.model.ContaBancaria;
import com.example.concorrencia.repository.ContaBancariaRepository;

@Service
public class ContaBancariaService {

	@Autowired
	private ContaBancariaRepository repository;
	
	@Transactional
	public void inicializarConta(BigDecimal saldoInicial) {
		ContaBancaria conta = new ContaBancaria();
		conta.setSaldo(saldoInicial);
		repository.save(conta);
	}
	
	@Transactional
	public void depositar(Long id, BigDecimal valor) {
		ContaBancaria conta = repository.findById(id)
				.orElseThrow(()-> new RuntimeException("Conta não encontrada."));
		conta.setSaldo(conta.getSaldo().add(valor));
		repository.save(conta);
	}
	
	@Transactional
	public void sacar(Long id, BigDecimal valor) {
		ContaBancaria conta = repository.findById(id)
				.orElseThrow(()-> new RuntimeException("Conta não encontrada."));
		if(conta.getSaldo().compareTo(valor)< 0) {
			throw new RuntimeException("Saldo insuficiente.");
		}
		conta.setSaldo(conta.getSaldo().subtract(valor));
		repository.save(conta);
	}
}
