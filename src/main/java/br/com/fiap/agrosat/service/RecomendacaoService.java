package br.com.fiap.agrosat.service;

import br.com.fiap.agrosat.dto.RecomendacaoRequest;
import br.com.fiap.agrosat.dto.RecomendacaoResponse;
import br.com.fiap.agrosat.entity.Recomendacao;
import br.com.fiap.agrosat.exception.ResourceNotFoundException;
import br.com.fiap.agrosat.repository.RecomendacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecomendacaoService {

    private final RecomendacaoRepository repository;

    public RecomendacaoService(RecomendacaoRepository repository) {
        this.repository = repository;
    }

    public List<RecomendacaoResponse> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public RecomendacaoResponse buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    public RecomendacaoResponse criar(RecomendacaoRequest req) {
        Recomendacao r = new Recomendacao();
        aplicar(r, req);
        return toResponse(repository.save(r));
    }

    public RecomendacaoResponse atualizar(Long id, RecomendacaoRequest req) {
        Recomendacao r = buscarEntidade(id);
        aplicar(r, req);
        return toResponse(repository.save(r));
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Recomendacao nao encontrada: " + id);
        }
        repository.deleteById(id);
    }

    private Recomendacao buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recomendacao nao encontrada: " + id));
    }

    private void aplicar(Recomendacao r, RecomendacaoRequest req) {
        r.setIdTalhao(req.idTalhao());
        r.setIdAlerta(req.idAlerta());
        r.setTipo(req.tipo());
        r.setTexto(req.texto());
        if (req.dataHora() != null) {
            r.setDataHora(req.dataHora());
        }
    }

    private RecomendacaoResponse toResponse(Recomendacao r) {
        return new RecomendacaoResponse(r.getId(), r.getIdTalhao(), r.getIdAlerta(),
                r.getTipo(), r.getTexto(), r.getDataHora());
    }
}
