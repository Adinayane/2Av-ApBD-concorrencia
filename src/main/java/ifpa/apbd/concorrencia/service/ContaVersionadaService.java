package ifpa.apbd.concorrencia.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ifpa.apbd.concorrencia.model.ContaBancariaVersionada;
import ifpa.apbd.concorrencia.repository.ContaVersionadaRepository;

@Service
public class ContaVersionadaService {
	
	@Autowired
	private ContaVersionadaRepository repository;

	@Transactional
	public ContaBancariaVersionada inicializarConta(BigDecimal saldoInicial) {
		ContaBancariaVersionada conta = new ContaBancariaVersionada();
		conta.setSaldo(saldoInicial);
		return repository.save(conta);
	}

	@Transactional
	public ContaBancariaVersionada depositar(Long id, BigDecimal valor) {
		ContaBancariaVersionada conta = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Conta não encontrada."));
		
		if(valor.compareTo(BigDecimal.ZERO) <= 0 ) {
			throw new RuntimeException("Valor inválido para depósito.");
		}
		conta.setSaldo(conta.getSaldo().add(valor));
		return repository.save(conta);
	}

	@Transactional
	public ContaBancariaVersionada sacar(Long id, BigDecimal valor) {
		ContaBancariaVersionada conta = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Conta não encontrada."));
		if (conta.getSaldo().compareTo(valor) < 0) {
			throw new RuntimeException("Saldo insuficiente.");
		}
		conta.setSaldo(conta.getSaldo().subtract(valor));
		return repository.save(conta);
	}

}
