package ifpa.apbd.concorrencia.controller;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ifpa.apbd.concorrencia.model.ContaBancaria;
import ifpa.apbd.concorrencia.service.ContaBancariaService;

@RestController
@RequestMapping("/contas")
public class ContaBancariaController {

    @Autowired
    private ContaBancariaService service;

    @PostMapping("/inicializar")
    public String inicializar() {
        ContaBancaria novaConta = service.inicializarConta(new BigDecimal("1000.00"));
        return "Conta " + novaConta.getId() + " inicializada com saldo de R$ 1000.00";
    }

    @PostMapping("/{id}/deposito")
    public String depositar(@PathVariable Long id, @RequestParam BigDecimal valor) {
        ContaBancaria contaAtualizada = service.depositar(id, valor);
        BigDecimal saldoAnterior = contaAtualizada.getSaldo().subtract(valor);
        
        return "=== COMPROVANTE DE DEPÓSITO ===\n" +
               "Conta: " + id + "\n" +
               "Saldo Anterior: R$ " + saldoAnterior + "\n" +
               "Valor Depositado: R$ " + valor + "\n" +
               "Saldo Atual: R$ " + contaAtualizada.getSaldo();
    }

    @PostMapping("/{id}/saque")
    public String sacar(@PathVariable Long id, @RequestParam BigDecimal valor) {
        ContaBancaria contaAtualizada = service.sacar(id, valor);
        BigDecimal saldoAnterior = contaAtualizada.getSaldo().add(valor);
        
        return "=== COMPROVANTE DE SAQUE ===\n" +
               "Conta: " + id + "\n" +
               "Saldo Anterior: R$ " + saldoAnterior + "\n" +
               "Valor Sacado: R$ " + valor + "\n" +
               "Saldo Atual: R$ " + contaAtualizada.getSaldo();
    }
}