package br.com.fiap.agrosat.repository;

import br.com.fiap.agrosat.entity.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {
}
