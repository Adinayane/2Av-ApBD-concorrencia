package ifpa.apbd.concorrencia.controller;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ifpa.apbd.concorrencia.model.ContaBancariaVersionada;
import ifpa.apbd.concorrencia.service.ContaVersionadaService;

@RestController
@RequestMapping("/contas-versionadas")
public class ContaVersionadaController {

    @Autowired
    private ContaVersionadaService service;

    @PostMapping("/inicializar")
    public String inicializar() {
        ContaBancariaVersionada novaConta = service.inicializarConta(new BigDecimal("1000.00"));
        return "Conta " + novaConta.getId() + " inicializada com saldo de R$ 1000.00";
    }

    @PostMapping("/{id}/deposito")
    public ResponseEntity<String> depositar(@PathVariable Long id, @RequestParam BigDecimal valor) {
        try {
            ContaBancariaVersionada contaAtualizada = service.depositar(id, valor);
            BigDecimal saldoAnterior = contaAtualizada.getSaldo().subtract(valor);
            
            String comprovante = "=== COMPROVANTE DE DEPÓSITO ===\n" +
                   "Conta: " + id + "\n" +
                   "Saldo Anterior: R$ " + saldoAnterior + "\n" +
                   "Valor Depositado: R$ " + valor + "\n" +
                   "Saldo Atual: R$ " + contaAtualizada.getSaldo();
            
            return ResponseEntity.ok(comprovante);
            
        } catch (ObjectOptimisticLockingFailureException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Erro de Concorrência: A conta foi atualizada por outro usuário. Depósito rejeitado.");
        }
    }

    @PostMapping("/{id}/saque")
    public ResponseEntity<String> sacar(@PathVariable Long id, @RequestParam BigDecimal valor) {
        try {
            ContaBancariaVersionada contaAtualizada = service.sacar(id, valor);
            BigDecimal saldoAnterior = contaAtualizada.getSaldo().add(valor);
            
            String comprovante = "=== COMPROVANTE DE SAQUE ===\n" +
                   "Conta: " + id + "\n" +
                   "Saldo Anterior: R$ " + saldoAnterior + "\n" +
                   "Valor Sacado: R$ " + valor + "\n" +
                   "Saldo Atual: R$ " + contaAtualizada.getSaldo();
            
            return ResponseEntity.ok(comprovante);
            
        } catch (ObjectOptimisticLockingFailureException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Erro de Concorrência: A conta foi atualizada por outro usuário. Saque rejeitado.");
        }
    }
}

