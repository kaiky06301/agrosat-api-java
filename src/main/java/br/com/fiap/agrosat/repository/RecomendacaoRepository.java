package br.com.fiap.agrosat.repository;

import br.com.fiap.agrosat.entity.Recomendacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecomendacaoRepository extends JpaRepository<Recomendacao, Long> {
}
