package br.com.fiap.agrosat.repository;

import br.com.fiap.agrosat.entity.RegistroEquipamento;
import br.com.fiap.agrosat.entity.RegistroEquipamentoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistroEquipamentoRepository extends JpaRepository<RegistroEquipamento, RegistroEquipamentoId> {
}
