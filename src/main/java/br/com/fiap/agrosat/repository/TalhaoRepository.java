package br.com.fiap.agrosat.repository;

import br.com.fiap.agrosat.entity.Talhao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TalhaoRepository extends JpaRepository<Talhao, Long> {
}
