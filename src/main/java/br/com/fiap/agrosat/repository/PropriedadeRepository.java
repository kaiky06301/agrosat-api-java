package br.com.fiap.agrosat.repository;

import br.com.fiap.agrosat.entity.Propriedade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropriedadeRepository extends JpaRepository<Propriedade, Long> {
}
