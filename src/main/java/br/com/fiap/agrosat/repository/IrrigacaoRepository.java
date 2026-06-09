package br.com.fiap.agrosat.repository;

import br.com.fiap.agrosat.entity.Irrigacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IrrigacaoRepository extends JpaRepository<Irrigacao, Long> {
}
