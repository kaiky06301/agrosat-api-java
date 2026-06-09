package br.com.fiap.agrosat.repository;

import br.com.fiap.agrosat.entity.DadoSatelite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DadoSateliteRepository extends JpaRepository<DadoSatelite, Long> {
}
