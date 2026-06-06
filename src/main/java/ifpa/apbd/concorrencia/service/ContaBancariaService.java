package ifpa.apbd.concorrencia.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ifpa.apbd.concorrencia.model.ContaBancaria;
import ifpa.apbd.concorrencia.repository.ContaBancariaRepository;

@Service
public class ContaBancariaService {

	@Autowired
	private ContaBancariaRepository repository;

	@Transactional
	public ContaBancaria inicializarConta(BigDecimal saldoInicial) {
		ContaBancaria conta = new ContaBancaria();
		conta.setSaldo(saldoInicial);
		return repository.save(conta);
	}

	@Transactional
	public ContaBancaria depositar(Long id, BigDecimal valor) {
		ContaBancaria conta = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Conta não encontrada."));
		
		if(valor.compareTo(BigDecimal.ZERO) <= 0 ) {
			throw new RuntimeException("Valor inválido para depósito.");
		}
		conta.setSaldo(conta.getSaldo().add(valor));
		return repository.save(conta);
	}

	@Transactional
	public ContaBancaria sacar(Long id, BigDecimal valor) {
		ContaBancaria conta = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Conta não encontrada."));
		if (conta.getSaldo().compareTo(valor) < 0) {
			throw new RuntimeException("Saldo insuficiente.");
		}
		conta.setSaldo(conta.getSaldo().subtract(valor));
		return repository.save(conta);
	}
}
