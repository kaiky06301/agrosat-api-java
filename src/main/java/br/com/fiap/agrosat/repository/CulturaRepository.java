package br.com.fiap.agrosat.repository;

import br.com.fiap.agrosat.entity.Cultura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CulturaRepository extends JpaRepository<Cultura, Long> {
}
