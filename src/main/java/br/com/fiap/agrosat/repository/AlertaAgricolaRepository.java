package br.com.fiap.agrosat.repository;

import br.com.fiap.agrosat.entity.AlertaAgricola;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertaAgricolaRepository extends JpaRepository<AlertaAgricola, Long> {
}
