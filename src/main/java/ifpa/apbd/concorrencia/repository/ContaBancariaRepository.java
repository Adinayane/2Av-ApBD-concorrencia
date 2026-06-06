package ifpa.apbd.concorrencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ifpa.apbd.concorrencia.model.ContaBancaria;

public interface ContaBancariaRepository extends JpaRepository<ContaBancaria, Long> {

}
