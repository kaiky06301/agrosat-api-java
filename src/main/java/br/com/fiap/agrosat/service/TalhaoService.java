package br.com.fiap.agrosat.service;

import br.com.fiap.agrosat.dto.TalhaoRequest;
import br.com.fiap.agrosat.dto.TalhaoResponse;
import br.com.fiap.agrosat.entity.Talhao;
import br.com.fiap.agrosat.exception.ResourceNotFoundException;
import br.com.fiap.agrosat.repository.TalhaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TalhaoService {

    private final TalhaoRepository repository;

    public TalhaoService(TalhaoRepository repository) {
        this.repository = repository;
    }

    public List<TalhaoResponse> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public TalhaoResponse buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    public TalhaoResponse criar(TalhaoRequest req) {
        Talhao t = new Talhao();
        aplicar(t, req);
        return toResponse(repository.save(t));
    }

    public TalhaoResponse atualizar(Long id, TalhaoRequest req) {
        Talhao t = buscarEntidade(id);
        aplicar(t, req);
        return toResponse(repository.save(t));
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Talhao nao encontrado: " + id);
        }
        repository.deleteById(id);
    }

    private Talhao buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Talhao nao encontrado: " + id));
    }

    private void aplicar(Talhao t, TalhaoRequest req) {
        t.setNome(req.nome());
        t.setAreaHa(req.areaHa());
        t.setIdPropriedade(req.idPropriedade());
        t.setIdCultura(req.idCultura());
    }

    private TalhaoResponse toResponse(Talhao t) {
        return new TalhaoResponse(t.getId(), t.getNome(), t.getAreaHa(),
                t.getIdPropriedade(), t.getIdCultura());
    }
}
