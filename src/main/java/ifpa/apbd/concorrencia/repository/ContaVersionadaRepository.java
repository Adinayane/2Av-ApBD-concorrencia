package ifpa.apbd.concorrencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ifpa.apbd.concorrencia.model.ContaBancariaVersionada;

public interface ContaVersionadaRepository extends JpaRepository<ContaBancariaVersionada, Long>{

}
