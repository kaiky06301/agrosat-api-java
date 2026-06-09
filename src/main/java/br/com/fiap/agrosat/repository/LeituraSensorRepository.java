package br.com.fiap.agrosat.repository;

import br.com.fiap.agrosat.entity.LeituraSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeituraSensorRepository extends JpaRepository<LeituraSensor, Long> {
}
