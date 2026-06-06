package com.example.concorrencia.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.concorrencia.service.ContaBancariaService;

@RestController
@RequestMapping("/contas")
public class ContaBancariaController {

	@Autowired
	private ContaBancariaService service;
	
	@PostMapping("/inicializar")
	public String inicializar() {
		service.inicializarConta(new BigDecimal("1000.00"));
		return "Conta 1 inicializada com saldo de R$1000.00";
	}
	
	@PostMapping("/{id}/deposito")
    public String depositar(@PathVariable Long id, @RequestParam BigDecimal valor) {
        service.depositar(id, valor);
        return "Depósito de R$ " + valor + " realizado com sucesso.";
    }

    @PostMapping("/{id}/saque")
    public String sacar(@PathVariable Long id, @RequestParam BigDecimal valor) {
        service.sacar(id, valor);
        return "Saque de R$ " + valor + " realizado com sucesso.";
    }
}
